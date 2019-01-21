package io.bhex.api.test;

import io.bhex.api.client.BHexApiClientFactory;
import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.BHexApiWebSocketClient;
import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.test.constant.Constants;

//@Slf4j
public class UserDataStreamTest {

    public static void main(String[] args) {
//
        BHexApiWebSocketClient client = BHexApiClientFactory.newInstance().newWebSocketClient();
        BHexApiRestClient restClient = BHexApiClientFactory.newInstance(Constants.ACCESS_KEY, Constants.SECRET_KEY).newRestClient();

        System.out.println("\n ------Get Listen Key -----");
        System.out.println();
        String listenKey = restClient.startUserDataStream(BHexConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println("listenKey:" + listenKey);
        // order
        client.onUserEvent(listenKey, response -> System.out.println(response), true);

    }
}
