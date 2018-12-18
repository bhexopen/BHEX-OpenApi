package io.bhex.api.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.bhex.api.client.BHexApiCallback;
import io.bhex.api.client.domain.account.SocketAccount;
import io.bhex.api.client.domain.account.SocketOrder;
import io.bhex.api.client.domain.account.SocketUserResponse;
import io.bhex.api.client.domain.channel.EventType;
import io.bhex.api.client.exception.BHexApiException;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.util.List;

/**
 * BHex API WebSocket listener.
 */
public class BHexApiWebSocketUserListener<T> extends WebSocketListener {

    private BHexApiCallback<T> callback;

    private boolean closing = false;

    public BHexApiWebSocketUserListener(BHexApiCallback<T> callback ) {
        this.callback = callback;
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            JsonNode jsonNode = mapper.readTree(text);

            Long pingTime = 0L;
            List<SocketOrder> orderList = Lists.newArrayList();
            List<SocketAccount> accountList = Lists.newArrayList();
            if (jsonNode.isArray()) {
                for (int i = 0; i < jsonNode.size(); i++) {

                    JsonNode node = jsonNode.get(i);
                    if (node == null) {
                        continue;
                    }

                    String eventType = node.get("e").asText();
                    if (eventType.equals(EventType.ACCOUNT_INFO.getType())) {
                        SocketAccount account = mapper.readValue(node.toString(), SocketAccount.class);
                        accountList.add(account);
                    } else if (eventType.equals(EventType.EXECUTION_REPORT.getType())) {
                        SocketOrder order = mapper.readValue(node.toString(),SocketOrder.class);
                        orderList.add(order);
                    }
                }
            } else {
                pingTime = jsonNode.get("ping").asLong();
            }

            SocketUserResponse event = SocketUserResponse.builder()
                    .pingTime(pingTime)
                    .orderList(orderList)
                    .accountList(accountList)
                    .build();

            callback.onResponse((T) event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BHexApiException(e);
        }
    }

    @Override
    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
        closing = true;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (!closing) {
            callback.onFailure(t);
        }
    }
}