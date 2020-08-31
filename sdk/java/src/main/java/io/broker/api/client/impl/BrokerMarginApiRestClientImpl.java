package io.broker.api.client.impl;

import io.broker.api.client.BrokerMarginApiRestClient;
import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.account.request.*;
import io.broker.api.client.domain.margin.*;
import io.broker.api.client.domain.margin.request.*;
import io.broker.api.client.service.BrokerMarginApiService;

import java.util.List;

public class BrokerMarginApiRestClientImpl implements BrokerMarginApiRestClient {
    private final BrokerMarginApiService brokerMarginApiService;

    public BrokerMarginApiRestClientImpl(String baseUrl, String apiKey, String secret) {
        brokerMarginApiService = BrokerApiServiceGenerator.createService(baseUrl, BrokerMarginApiService.class, apiKey, secret);
    }

    @Override
    public NewOrderResponse newOrder(NewOrder order) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
                order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getRecvWindow(), order.getTimestamp()));
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getOrderStatus(orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.cancelOrder(cancelOrderRequest.getOrderId(), cancelOrderRequest.getClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getOpenOrders(OpenOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getLimit(),
                orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getHistoryOrders(HistoryOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getHistroyOrders(orderRequest.getOrderId(), orderRequest.getStartTime(), orderRequest.getEndTime(),
                orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public Account getAccount(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getAccount(recvWindow, timestamp));
    }

    @Override
    public List<Trade> getMyTrades(MyTradeRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getMyTrades(request.getFromId(), request.getToId(), request.getStartTime(), request.getEndTime(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public MarginBaseResponse openMargin(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.openMargin(recvWindow, timestamp));
    }

    @Override
    public MarginSafetyResponse getMarginSafety(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getMarginSafety(recvWindow, timestamp));
    }

    @Override
    public List<MarginToken> queryMarginToken() {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryMarginToken());
    }

    @Override
    public MarginRiskConfigResponse getMarginRiskConfig() {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getMarginRisk());
    }

    @Override
    public MarginLoanableResponse getMarginLoanable(MarginLoanableRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getMarginLoanable(request.getTokenId(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public List<MarginLoanPosition> queryLoanPosition(MarginLoanPositionRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryLoanPosition(request.getTokenId(), request.getRecvWindow(), request.getRecvWindow()));
    }

    @Override
    public MarginAvailWithdrawResponse getAvailWithdraw(MarginAvailWithdrawRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getAvailWithdraw(request.getTokenId(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public MarginBaseResponse marginTransfer(MarginTransferRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.marginTransfer(request.getFromAccountType(), request.getToAccountType(), request.getTokenId(),
                request.getAmount(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public LoanResponse loan(LoanRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.loan(request.getClientOrderId(), request.getLoanAmount(), request.getTokenId(),
                request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public RepayResponse repay(RepayRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.repay(request.getClientOrderId(), request.getLoanOrderId(), request.getRepayType(),
                request.getRepayAmount(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public MarginAllPositionResponse queryAllPosition(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryAllPosition(recvWindow, timestamp));
    }

    @Override
    public List<LoanOrder> queryLoanOrders(QueryLoanOdersRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryLoanOrders(request.getTokenId(), request.getStatus(), request.getFromLoanId(),
                request.getEndLoanId(), request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public LoanOrder getLoanOrder(Long loanOrderId, Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getLoanOrder(loanOrderId, recvWindow, timestamp));
    }

    @Override
    public List<RepayOrder> queryRepayOrders(QueryRepayOrdersRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryRepayOrders(request.getTokenId(), request.getFromRepayId(), request.getEndRepayId(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public List<RepayOrder> queryRepayOrderByLoanId(QueryRepayOrdersRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.getRepayOrderByLoanId(request.getLoanOrderId(), request.getFromRepayId(), request.getEndRepayId(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public List<MarginBalanceFlow> queryBalaceFlow(GetMarginBalanceFlowRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerMarginApiService.queryBalanceFlow(request.getTokenId(),request.getFromFlowId(),request.getEndFlowId(),request.getStartTime(),
                request.getEndTime(),request.getLimit(),request.getRecvWindow(),request.getTimestamp()));
    }
}
