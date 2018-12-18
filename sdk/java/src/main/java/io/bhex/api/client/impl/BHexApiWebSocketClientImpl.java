package io.bhex.api.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhex.api.client.BHexApiCallback;
import io.bhex.api.client.BHexApiWebSocketClient;
import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.client.domain.account.Order;
import io.bhex.api.client.domain.account.SocketUserResponse;
import io.bhex.api.client.domain.channel.ChannelRequest;
import io.bhex.api.client.domain.channel.EventTopic;
import io.bhex.api.client.domain.channel.EventType;
import io.bhex.api.client.domain.event.CandlestickEvent;
import io.bhex.api.client.domain.event.DepthEvent;
import io.bhex.api.client.domain.event.TickerEvent;
import io.bhex.api.client.domain.event.TradeEvent;
import io.bhex.api.client.domain.market.CandlestickInterval;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.Closeable;

/**
 * BHex API WebSocket client implementation using OkHttp.
 */
public class BHexApiWebSocketClientImpl implements BHexApiWebSocketClient, Closeable {

    private final OkHttpClient client;

    public BHexApiWebSocketClientImpl(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public Closeable onDepthEvent(String symbols, BHexApiCallback<DepthEvent> callback) {

        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.DEPTH.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BHexApiWebSocketListener<>(callback, DepthEvent.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onCandlestickEvent(String symbols, CandlestickInterval interval, BHexApiCallback<CandlestickEvent> callback) {
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.KLINE.getTopic(interval.getIntervalId()));
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BHexApiWebSocketListener<>(callback, CandlestickEvent.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Closeable onTradeEvent(String symbols, BHexApiCallback<TradeEvent> callback){
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.TRADES.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BHexApiWebSocketListener<>(callback, TradeEvent.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Closeable onTicker24HourEvent(String symbols, BHexApiCallback<TickerEvent> callback){
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.REALTIMES.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BHexApiWebSocketListener<>(callback, TickerEvent.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onUserEvent(String listenKey, BHexApiCallback<SocketUserResponse> callback) {
        ChannelRequest request = new ChannelRequest();
        request.setTopic(EventTopic.ORDER.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewUserWebSocket(channel,listenKey, new BHexApiWebSocketUserListener<>(callback));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @deprecated This method is no longer functional. Please use the returned {@link Closeable} from any of the other methods to close the web socket.
     */
    @Override
    public void close() {
    }

    private Closeable createNewWebSocket(String channel, BHexApiWebSocketListener<?> listener) {
        String streamingUrl = String.format("%s/%s", BHexConstants.WS_API_BASE_URL, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(channel);
        return createCloseable(listener,webSocket);
    }

    private Closeable createNewUserWebSocket(String channel, String listenKey ,BHexApiWebSocketUserListener<?> listener) {
        Request request = new Request.Builder().url(BHexConstants.WS_API_USER_URL+listenKey).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(channel);
        return createCloseable(listener,webSocket);
    }

    public Closeable createCloseable(WebSocketListener listener, final WebSocket webSocket){
        try {
            return () -> {
                final int code = 1000;
                listener.onClosing(webSocket, code, null);
                webSocket.close(code, null);
                listener.onClosed(webSocket, code, null);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
