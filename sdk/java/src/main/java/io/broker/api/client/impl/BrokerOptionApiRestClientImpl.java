package io.broker.api.client.impl;

import java.util.List;

import io.broker.api.client.BrokerOptionApiRestClient;
import io.broker.api.client.domain.account.request.CancelOrderRequest;
import io.broker.api.client.domain.option.OptionMatchResult;
import io.broker.api.client.domain.option.OptionOrderResult;
import io.broker.api.client.domain.option.OrderResult;
import io.broker.api.client.domain.option.PositionResult;
import io.broker.api.client.domain.option.SettlementResult;
import io.broker.api.client.domain.option.TokenOptionResult;
import io.broker.api.client.domain.option.request.GetOrderRequest;
import io.broker.api.client.domain.option.request.OptionHistoryOrderRequest;
import io.broker.api.client.domain.option.request.OptionOpenOrderRequest;
import io.broker.api.client.domain.option.request.OptionOrderRequest;
import io.broker.api.client.domain.option.request.OptionPositionRequest;
import io.broker.api.client.domain.option.request.OptionSettlementRequest;
import io.broker.api.client.domain.option.request.OptionTradeRequest;
import io.broker.api.client.domain.option.request.OptionsRequest;
import io.broker.api.client.service.BrokerOptionApiService;

/**
 * Implementation of Broker's Option REST API using Retrofit with synchronous/blocking method
 * calls.
 */
public class BrokerOptionApiRestClientImpl implements BrokerOptionApiRestClient {

    private final BrokerOptionApiService brokerOptionApiService;

    public BrokerOptionApiRestClientImpl(String baseUrl, String apiKey, String secret) {
        brokerOptionApiService = BrokerApiServiceGenerator.createService(baseUrl, BrokerOptionApiService.class, apiKey, secret);
    }

    @Override
    public List<TokenOptionResult> getOptions(OptionsRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptions(request.getExpired()));
    }

    @Override
    public OptionOrderResult newOptionOrder(OptionOrderRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.newOptionOrder(
                request.getSymbol(),
                request.getOrderSide() == null ? "" : request.getOrderSide().name(),
                request.getOrderType() == null ? "" : request.getOrderType().name(),
                request.getTimeInForce().name(),
                request.getQuantity(),
                request.getPrice(),
                request.getClientOrderId(),
                request.getRecvWindow(),
                request.getTimestamp()
        ));
    }

    @Override
    public OptionOrderResult cancelOptionOrder(CancelOrderRequest cancelOrderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.cancelOptionOrder(
                cancelOrderRequest.getOrderId(),
                cancelOrderRequest.getClientOrderId(),
                cancelOrderRequest.getRecvWindow(),
                cancelOrderRequest.getTimestamp()
        ));
    }

    @Override
    public List<OptionOrderResult> getOptionOpenOrders(OptionOpenOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptionOpenOrders(
                orderRequest.getSymbol(),
                orderRequest.getOrderId(),
                orderRequest.getLimit(),
                orderRequest.getOrderSide() == null ? "" : orderRequest.getOrderSide().name(),
                orderRequest.getOrderType() == null ? "" : orderRequest.getOrderType().name(),
                orderRequest.getRecvWindow(),
                orderRequest.getTimestamp()
        ));
    }

    @Override
    public List<OptionOrderResult> getOptionHistoryOrders(OptionHistoryOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptionHistoryOrders(
                orderRequest.getSymbol(),
                orderRequest.getOrderSide() == null ? "" : orderRequest.getOrderSide().name(),
                orderRequest.getOrderType() == null ? "" : orderRequest.getOrderType().name(),
                orderRequest.getLimit(),
                orderRequest.getOrderStatus() == null ? "" : orderRequest.getOrderStatus().name(),
                orderRequest.getRecvWindow(),
                orderRequest.getTimestamp()
        ));
    }

    @Override
    public List<OptionMatchResult> getOptionMyTrades(OptionTradeRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptionMyTrades(
                request.getSymbol(),
                request.getFromId(),
                request.getToId(),
                request.getLimit(),
                request.getOrderSide() == null ? "" : request.getOrderSide().name(),
                request.getRecvWindow(),
                request.getTimestamp()
        ));
    }

    @Override
    public List<PositionResult> getOptionPositions(OptionPositionRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptionPositions(
                request.getSymbol(),
                request.getRecvWindow(),
                request.getTimestamp()
        ));
    }

    @Override
    public List<SettlementResult> getOptionSettlements(OptionSettlementRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOptionSettlements(
                request.getSymbol(),
                request.getRecvWindow(),
                request.getTimestamp()
        ));
    }

    @Override
    public OrderResult getOrder(GetOrderRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerOptionApiService.getOrder(
                request.getOrderId(),
                request.getClientOrderId(),
                request.getRecvWindow(),
                request.getTimestamp()
        ));
    }
}
