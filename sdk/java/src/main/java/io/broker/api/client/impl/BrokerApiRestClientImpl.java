package io.broker.api.client.impl;

import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.domain.general.BrokerInfo;
import io.broker.api.client.service.BrokerApiService;
import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.account.request.*;
import io.broker.api.client.domain.market.*;

import java.util.List;

import static io.broker.api.client.impl.BrokerApiServiceGenerator.createService;

/**
 * Implementation of Broker's REST API using Retrofit with synchronous/blocking method calls.
 */
public class BrokerApiRestClientImpl implements BrokerApiRestClient {

    private final BrokerApiService brokerApiService;

    public BrokerApiRestClientImpl(String baseUrl, String apiKey, String secret) {
        brokerApiService = BrokerApiServiceGenerator.createService(baseUrl, BrokerApiService.class, apiKey, secret);
    }

    // General endpoints

    @Override
    public void ping() {
        BrokerApiServiceGenerator.executeSync(brokerApiService.ping());
    }

    @Override
    public Long getServerTime() {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getServerTime()).getServerTime();
    }

    @Override
    public BrokerInfo getBrokerInfo() {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getBrokerInfo());
    }

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getOrderBook(symbol, limit));
    }

    @Override
    public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getTrades(symbol, limit));
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Long startTime, Long endTime, Integer limit) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getCandlestickBars(symbol, interval.getIntervalId(), startTime, endTime, limit));
    }

    @Override
    public TickerStatistics get24HrPriceStatistics(String symbol) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.get24HrPriceStatistics(symbol));
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getLatestPrice(symbol));
    }

    @Override
    public BookTicker getBookTicker(String symbol) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getBookTicker(symbol));
    }

    @Override
    public Index getIndex(String symbol) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getIndex(symbol));
    }

    @Override
    public NewOrderResponse newOrder(NewOrder order) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
                order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
                order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()));
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getOrderStatus(orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.cancelOrder(cancelOrderRequest.getOrderId(), cancelOrderRequest.getClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getOpenOrders(OpenOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getLimit(),
                orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getHistoryOrders(HistoryOrderRequest orderRequest) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getHistroyOrders(orderRequest.getOrderId(), orderRequest.getStartTime(), orderRequest.getEndTime(),
                orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public Account getAccount(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getAccount(recvWindow, timestamp));
    }

    @Override
    public List<Trade> getMyTrades(MyTradeRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getMyTrades(request.getFromId(), request.getToId(), request.getStartTime(), request.getEndTime(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public List<DepositOrder> getDepositOrders(DepositOrderRequest request) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.getDepositOrders(request.getToken(), request.getStartTime(), request.getEndTime(), request.getFromId(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public String startUserDataStream(Long recvWindow, Long timestamp) {
        return BrokerApiServiceGenerator.executeSync(brokerApiService.startUserDataStream(recvWindow, timestamp)).toString();
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, Long recvWindow, Long timestamp) {
        BrokerApiServiceGenerator.executeSync(brokerApiService.keepAliveUserDataStream(listenKey, recvWindow, timestamp));
    }

    @Override
    public void closeUserDataStream(String listenKey, Long recvWindow, Long timestamp) {
        BrokerApiServiceGenerator.executeSync(brokerApiService.closeAliveUserDataStream(listenKey, recvWindow, timestamp));
    }

}
