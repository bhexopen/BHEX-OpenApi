package io.broker.api.test;

import io.broker.api.client.BrokerApiCallback;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiWebSocketClient;
import io.broker.api.client.domain.event.CandlestickEvent;
import io.broker.api.client.domain.event.DepthEvent;
import io.broker.api.client.domain.event.TickerEvent;
import io.broker.api.client.domain.event.TradeEvent;
import io.broker.api.client.domain.market.CandlestickInterval;
import io.broker.api.client.impl.BrokerApiWebSocketClientImpl;
import io.broker.api.test.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebsocketDelayTest {

    static BrokerApiClientFactory apiClientFactory = BrokerApiClientFactory
            .newInstance(Constants.API_BASE_URL);

    static String symbol = "BTCUSDT";

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("start test round {}", i);
            startTest(i);
        }

        log.info("finished all test");
    }

    static void startTest(int round) throws Exception {
        long start = System.currentTimeMillis();
        log.info("round {}, start test websocket delay at {}", round, start);
        DelayCheckCallback callback = new DelayCheckCallback(start, round);

        ConnectionPool connectionPool = new ConnectionPool(0, 10, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .build();
        BrokerApiWebSocketClient client = new BrokerApiWebSocketClientImpl(okHttpClient, Constants.WS_API_BASE_URL, Constants.WS_API_USER_URL);

        // depth
        Closeable depthChannel = client.onDepthEvent(symbol, callback);

        // kline
        Closeable klineChannel = client.onCandlestickEvent(symbol, CandlestickInterval.ONE_MINUTE, callback);

        // trades
        Closeable tradeChannel = client.onTradeEvent(symbol, callback);

        // ticker for 24 hour
        Closeable tickerChannel = client.onTicker24HourEvent(symbol, callback);

        // index
        //client.onIndexEvent("BTCUSDT", System.out::println);
        Thread.sleep(5 * 1000);

        // close all channel, and websocket connection
        depthChannel.close();
        klineChannel.close();
        tradeChannel.close();
        tickerChannel.close();

        client.close();
        okHttpClient.connectionPool().evictAll();

        log.info("round {}, end test, closed client", round);
        Thread.sleep(3 * 1000);
    }

    static class DelayCheckCallback<T> implements BrokerApiCallback<T> {
        boolean isFirst = true;
        long start;
        int round;

        DelayCheckCallback(long start, int round) {
            this.start = start;
            this.round = round;
        }

        @Override
        public void onResponse(T response) {
            long now = System.currentTimeMillis();

            if (isFirst) {
                isFirst = false;
                log.info("round {}, first response, delay {}", round, (now - start));
            }

            if (response instanceof TickerEvent) {
                TickerEvent tickerEvent = (TickerEvent) response;
                log.info("round {}, onTicker: {} - {} = {}", round, now, tickerEvent.getTime(), (now - tickerEvent.getTime()));
            } else if (response instanceof DepthEvent) {
                DepthEvent depthEvent = (DepthEvent) response;
                log.info("round {}, onDepth: {} - {} = {}", round, now, depthEvent.getEventTime(), (now - depthEvent.getEventTime()));
            } else if (response instanceof CandlestickEvent) {
                CandlestickEvent candlestickEvent = (CandlestickEvent) response;
                log.info("round {}, onKline: {} - {} = {}", round, now, candlestickEvent.getTime(), (now - candlestickEvent.getTime()));
            } else if (response instanceof TradeEvent) {
                TradeEvent tradeEvent = (TradeEvent) response;
                long time = tradeEvent.getItemList().get(0).getTime();
                // log.info("round {}, onTrade: {} - {} = {}", round, now, time, (now - time));
            } else {
                log.info("round {}, ignore response {}", round, response.getClass().getName());
            }
        }

        @Override
        public void onFailure(Throwable cause) {
            log.warn("onFailure: {}", cause.getMessage());
        }
    }
}
