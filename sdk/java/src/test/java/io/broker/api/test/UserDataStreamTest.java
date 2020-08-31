package io.broker.api.test;

import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.BrokerApiWebSocketClient;
import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.account.request.CancelOrderRequest;
import io.broker.api.client.domain.account.request.OrderStatusRequest;
import io.broker.api.test.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserDataStreamTest {

    static BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
    static BrokerApiWebSocketClient wsClient = factory.newWebSocketClient(Constants.WS_API_BASE_URL, Constants.WS_API_USER_URL);
    static BrokerApiRestClient restClient = factory.newRestClient();

    public static void main(String[] args) {

        log.info("\n ------Get Listen Key -----");
        String listenKey = restClient.startUserDataStream(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        log.info("listenKey:" + listenKey);

        // check received msg time delay
        wsClient.onUserEvent(listenKey, response -> {
            long now = System.currentTimeMillis();
            List<SocketOrder> orderList = response.getOrderList();
            List<SocketAccount> accountList = response.getAccountList();
            log.info("received event: {}", response);

            if (orderList != null && orderList.size() > 0) {
                SocketOrder order = orderList.get(0);
                long costTime1 = now - order.getCreateTime();
                long costTime2 = now - order.getEventTime();
                if (costTime2 > 100L) {
                    log.warn("received event {}, order id {}, \n -- order status {}, costTime1 {}, costTime2 {}",
                            order.getEventType(), order.getOrderId(), order.getStatus(), costTime1, costTime2);
                } else {
                    log.info("received event {}, order id {}, \n -- order status {}, costTime1 {}, costTime2 {}",
                            order.getEventType(), order.getOrderId(), order.getStatus(), costTime1, costTime2);
                }
            }

            if (accountList != null && accountList.size() > 0) {
                SocketAccount account = accountList.get(0);
                List<SocketBalance> balances = account.getBalanceChangedList();
                long costTime1 = now - account.getLastUpdated();
                long costTime2 = now - account.getEventTime();
                log.info("received event {}, Account Balance changed size {}, costTime1 {}, costTime2 {}",
                        account.getEventType(), balances.size(), costTime1, costTime2);
            }
        }, true);

        new Thread(() -> {
            startPlaceOrder();
        }).start();
    }

    private static void startPlaceOrder() {
        Account account = restClient.getAccount(5L, System.currentTimeMillis() / 1000);
        log.info("start place order using account: {}", account);

        for (int i = 0; i < 100; i++) {
            // HBCUSDT now price 3.2 , so it will not fill
            NewOrderResponse response = restClient.newOrder(NewOrder.limitBuy("HBCUSDT", TimeInForce.GTC, "10", "1"));
            safeSleep(100L);
            try {
                restClient.cancelOrder(new CancelOrderRequest(response.getOrderId()));
            } catch (Exception e) {
                log.error("cancel order catch exception, order {}, exception {}", response, e);

                Order order2 = restClient.getOrderStatus(new OrderStatusRequest(response.getOrderId()));
                log.info("order is {}", order2);
            }
            Order order = restClient.getOrderStatus(new OrderStatusRequest(response.getOrderId()));
            //log.info("new order no.{}, order id {}, status: {}", i, order.getOrderId(), order.getStatus());
            safeSleep(100L);
        }
    }

    private static void safeSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
