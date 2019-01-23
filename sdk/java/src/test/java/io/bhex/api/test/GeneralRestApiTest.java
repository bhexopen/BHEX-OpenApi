package io.bhex.api.test;

import io.bhex.api.client.BHexApiClientFactory;
import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.domain.general.BrokerInfo;
import io.bhex.api.test.constant.Constants;

public class GeneralRestApiTest {

    public static void main(String[] args) {

        BHexApiClientFactory factory = BHexApiClientFactory.newInstance();
        BHexApiRestClient client = factory.newRestClient();

        System.out.println("\n ------BrokerInfo-----");
        BrokerInfo brokerInfo = client.getBrokerInfo();
        System.out.println(brokerInfo);

    }


}
