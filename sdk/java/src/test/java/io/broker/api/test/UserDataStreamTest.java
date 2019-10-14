package io.broker.api.test;

import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.BrokerApiWebSocketClient;
import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.test.constant.Constants;

//@Slf4j
public class UserDataStreamTest {

    public static void main(String[] args) {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerApiWebSocketClient wsClient = factory.newWebSocketClient(Constants.WS_API_BASE_URL, Constants.WS_API_USER_URL);
        BrokerApiRestClient restClient = factory.newRestClient();

        System.out.println("\n ------Get Listen Key -----");
        System.out.println();
        String listenKey = restClient.startUserDataStream(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println("listenKey:" + listenKey);
        // order
        wsClient.onUserEvent(listenKey, System.out::println, true);
    }
}
