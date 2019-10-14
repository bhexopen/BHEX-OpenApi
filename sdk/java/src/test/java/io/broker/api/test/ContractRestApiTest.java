package io.broker.api.test;

import com.google.common.collect.Lists;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerContractApiRestClient;
import io.broker.api.test.constant.Constants;
import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ContractRestApiTest {

    public static void main(String[] args) {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerContractApiRestClient client = factory.newContractRestClient();

        System.out.println("\n ------new contract order-----");
        ContractOrderResult orderResult = client.newContractOrder(
            ContractOrderRequest.builder()
                    .symbol("BTC0808")
                    .leverage("10")
                    .orderType(OrderType.LIMIT)
                    .side(OrderSide.BUY_OPEN)
                    .price("8000")
                    .quantity("5")
                    .priceType(PriceType.INPUT)
                    .timeInForce(TimeInForce.GTC)
                    .triggerPrice("")
                    .clientOrderId(UUID.randomUUID().toString())
                    .build()
        );

        System.out.println(orderResult);

        System.out.println("\n ------get contract open orders-----");
        List<ContractOrderResult> openOrders = client.getContractOpenOrders(
                ContractOpenOrderRequest.builder()
                        .symbol("BTC0808")
                        .limit(10)
                        .side(OrderSide.BUY_OPEN)
                        .build()
        );
        System.out.println(openOrders);

        System.out.println("\n ------get contract history orders-----");
        List<ContractOrderResult> historyOrders = client.getContractHistoryOrders(
                ContractHistoryOrderRequest.builder()
                        .symbol("BTC0808")
                        .limit(10)
                        .side(OrderSide.BUY_OPEN)
                        .build()
        );
        System.out.println(historyOrders);

        System.out.println("\n ------cancel contract order-----");
        ContractOrderResult orderResultCancel = client.cancelContractOrder(
            CancelContractOrderRequest.builder()
                    .clientOrderId(orderResult.getClientOrderId())
                    .orderType(orderResult.getOrderType())
                    .build()
        );
        System.out.println(orderResultCancel);

        System.out.println("\n ------batch cancel contract order-----");
        BatchCancelOrderResult batchCancelOrderResult = client.batchCancelContractOrder(
                BatchCancelOrderRequest.builder()
                        .symbolList(Lists.newArrayList("BTC0808"))
                        .build()
        );

        System.out.println("\n ------get contract my trades-----");
        List<ContractMatchResult> matchResults = client.getContractMyTrades(
                ContractMyTradeRequest.builder()
                        .symbol("BTC0808")
                        //.side(OrderSide.BUY)
                        .limit(10)
                        .build()
        );
        System.out.println(matchResults);

        System.out.println("\n ------get contract positions-----");
        List<ContractPositionResult> positionResults = client.getContractPositions(
                ContractPositionRequest.builder()
                        .symbol("BTC0808")
                        .side(PositionSide.LONG)
                        .build()
        );
        System.out.println(positionResults);

        System.out.println("\n ------get contract account-----");
        Map<String, ContractAccountResult> accountResultMap = client.getContractAccount();
        System.out.println(accountResultMap);
    }
}
