package io.broker.api.test;

import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerMarginApiRestClient;
import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.account.request.*;
import io.broker.api.client.domain.margin.*;
import io.broker.api.client.domain.margin.request.*;
import io.broker.api.test.constant.Constants;

import java.util.List;

public class MarginRestApiTest {
    public static void main(String[] args) {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerMarginApiRestClient client = factory.newMarginRestClient();

        System.out.println("\n ------open margin-----");
        MarginBaseResponse response1 = client.openMargin(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(response1);

        System.out.println("\n ------get safety-----");
        MarginSafetyResponse response2 = client.getMarginSafety(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(response2);

        System.out.println("\n ------get account-----");
        Account response3 = client.getAccount(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(response3);

        System.out.println("\n ------limit buy-----");
        NewOrderResponse response4 = client.newOrder(NewOrder.limitBuy("BTCUSDT", TimeInForce.GTC, "1", "200"));
        System.out.println(response4);

        System.out.println("\n ------limit sell-----");
        NewOrderResponse response5 = client.newOrder(NewOrder.limitSell("BTCUSDT", TimeInForce.GTC, "0.01", "20000"));
        System.out.println(response5);

        System.out.println("\n ------market buy-----");
        NewOrderResponse response6 = client.newOrder(NewOrder.marketBuy("BTCUSDT", "200"));
        System.out.println(response6);

        System.out.println("\n ------market sell-----");
        NewOrderResponse response7 = client.newOrder(NewOrder.marketSell("BTCUSDT", "0.01"));
        System.out.println(response7);

        System.out.println("\n ------get order status-----");
        Order order = client.getOrderStatus(new OrderStatusRequest(687409405483948800L));
        System.out.println(order);

        System.out.println("\n ------cancel order-----");
        CancelOrderResponse cancelOrderResponse = client.cancelOrder(new CancelOrderRequest("1596684242525222"));
        System.out.println(cancelOrderResponse);

        System.out.println("\n ------get open orders-----");
        List<Order> openOrderList = client.getOpenOrders(new OpenOrderRequest("BTCUSDT", 5));
        System.out.println(openOrderList);

        System.out.println("\n ------get history orders-----");
        List<Order> historyOrderList = client.getHistoryOrders(new HistoryOrderRequest());
        System.out.println(historyOrderList);

        System.out.println("\n ------get trades -----");
        List<Trade> tradeList = client.getMyTrades(new MyTradeRequest(5));
        System.out.println(tradeList);

        System.out.println("\n ------get margin token -----");
        List<MarginToken> tokens = client.queryMarginToken();
        System.out.println(tokens);

        System.out.println("\n ------get margin risk -----");
        MarginRiskConfigResponse risk = client.getMarginRiskConfig();
        System.out.println(risk);

        System.out.println("\n ------get margin loanable -----");
        MarginLoanableResponse loanable = client.getMarginLoanable(new MarginLoanableRequest("USDT"));
        System.out.println(loanable);

        System.out.println("\n ------get margin loanPosition -----");
        List<MarginLoanPosition> loanPosition = client.queryLoanPosition(new MarginLoanPositionRequest());
        System.out.println(loanPosition);

        System.out.println("\n ------get margin avail withdraw -----");
        MarginAvailWithdrawResponse avail = client.getAvailWithdraw(new MarginAvailWithdrawRequest("USDT"));
        System.out.println(avail);

        System.out.println("\n ------get margin transfer -----");
        MarginBaseResponse transfer = client.marginTransfer(new MarginTransferRequest(27,1,"USDT","10"));
        System.out.println(transfer);

        System.out.println("\n ------ loan -----");
        LoanResponse loan = client.loan(new LoanRequest("10","USDT"));
        System.out.println(loan);

        System.out.println("\n ------ repay -----");
        RepayResponse repay = client.repay(new RepayRequest(691972597732174336L,0));
        System.out.println(repay);


        System.out.println("\n ------ query margin all posi -----");
        MarginAllPositionResponse allPosi = client.queryAllPosition(BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(allPosi);

        System.out.println("\n ------ query loan orders -----");
        List<LoanOrder> loanOrders = client.queryLoanOrders(new QueryLoanOdersRequest(0,50));
        System.out.println(loanOrders);

        System.out.println("\n ------ get loan order -----");
        LoanOrder loanOrder = client.getLoanOrder(691981194981631488L,BrokerConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(loanOrder);

        System.out.println("\n ------ query repay orders -----");
        List<RepayOrder> repayOrders = client.queryRepayOrders(new QueryRepayOrdersRequest());
        System.out.println(repayOrders);

        System.out.println("\n ------ query repay orders by loanOrderId -----");
        List<RepayOrder> repayOrders1 = client.queryRepayOrderByLoanId(new QueryRepayOrdersRequest(691981194981631488L));
        System.out.println(repayOrders1);

        System.out.println("\n ------ query margin balance flow -----");
        List<MarginBalanceFlow> flows = client.queryBalaceFlow(new GetMarginBalanceFlowRequest());
        System.out.println(flows);

    }
}
