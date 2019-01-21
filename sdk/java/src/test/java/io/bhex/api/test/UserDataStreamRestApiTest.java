package io.bhex.api.test;

import io.bhex.api.client.BHexApiClientFactory;
import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.test.constant.Constants;

public class UserDataStreamRestApiTest {

    public static void main(String[] args) {

        BHexApiClientFactory factory = BHexApiClientFactory.newInstance(Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BHexApiRestClient client = factory.newRestClient();

        System.out.println("\n ------start user data stream-----");
        String listenKey = client.startUserDataStream(BHexConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(listenKey);

        System.out.println("\n ------keepAlive user data stream-----");
        client.keepAliveUserDataStream(listenKey, BHexConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());

        System.out.println("\n ------close user data stream-----");
        client.closeUserDataStream(listenKey, BHexConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());

    }

}
