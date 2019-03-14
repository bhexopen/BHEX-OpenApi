package io.bhex.api.client.impl;

import io.bhex.api.client.BHexApiRestClient;
import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.client.domain.account.*;
import io.bhex.api.client.domain.account.request.*;
import io.bhex.api.client.domain.general.BrokerInfo;
import io.bhex.api.client.domain.market.*;
import io.bhex.api.client.service.BHexApiService;

import java.util.List;

import static io.bhex.api.client.impl.BHexApiServiceGenerator.createService;
import static io.bhex.api.client.impl.BHexApiServiceGenerator.executeSync;

/**
 * Implementation of BHex's REST API using Retrofit with synchronous/blocking method calls.
 */
public class BHexApiRestClientImpl implements BHexApiRestClient {

    private final BHexApiService bHexApiService;

    public BHexApiRestClientImpl(String baseUrl, String apiKey, String secret) {
        bHexApiService = createService(baseUrl, BHexApiService.class, apiKey, secret);
    }

    // General endpoints

    @Override
    public void ping() {
        executeSync(bHexApiService.ping());
    }

    @Override
    public Long getServerTime() {
        return executeSync(bHexApiService.getServerTime()).getServerTime();
    }

    @Override
    public BrokerInfo getBrokerInfo() {
        return executeSync(bHexApiService.getBrokerInfo());
    }

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return executeSync(bHexApiService.getOrderBook(symbol, limit));
    }

    @Override
    public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
        return executeSync(bHexApiService.getTrades(symbol, limit));
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Long startTime, Long endTime, Integer limit) {
        return executeSync(bHexApiService.getCandlestickBars(symbol, interval.getIntervalId(), startTime, endTime, limit));
    }

    @Override
    public TickerStatistics get24HrPriceStatistics(String symbol) {
        return executeSync(bHexApiService.get24HrPriceStatistics(symbol));
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        return executeSync(bHexApiService.getLatestPrice(symbol));
    }

    @Override
    public BookTicker getBookTicker(String symbol) {
        return executeSync(bHexApiService.getBookTicker(symbol));
    }

    @Override
    public Index getIndex(String symbol) {
        return executeSync(bHexApiService.getIndex(symbol));
    }

    @Override
    public NewOrderResponse newOrder(NewOrder order) {
        return executeSync(bHexApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
                order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
                order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()));
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        return executeSync(bHexApiService.getOrderStatus(orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        return executeSync(bHexApiService.cancelOrder(cancelOrderRequest.getOrderId(), cancelOrderRequest.getClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getOpenOrders(OpenOrderRequest orderRequest) {
        return executeSync(bHexApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getLimit(),
                orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public List<Order> getHistoryOrders(HistoryOrderRequest orderRequest) {
        return executeSync(bHexApiService.getHistroyOrders(orderRequest.getOrderId(), orderRequest.getStartTime(), orderRequest.getEndTime(),
                orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
    }

    @Override
    public Account getAccount(Long recvWindow, Long timestamp) {
        return executeSync(bHexApiService.getAccount(recvWindow, timestamp));
    }

    @Override
    public List<Trade> getMyTrades(MyTradeRequest request) {
        return executeSync(bHexApiService.getMyTrades(request.getFromId(), request.getToId(), request.getStartTime(), request.getEndTime(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public List<DepositOrder> getDepositOrders(DepositOrderRequest request) {
        return executeSync(bHexApiService.getDepositOrders(request.getToken(), request.getStartTime(), request.getEndTime(), request.getFromId(),
                request.getLimit(), request.getRecvWindow(), request.getTimestamp()));
    }

    @Override
    public String startUserDataStream(Long recvWindow, Long timestamp) {
        return executeSync(bHexApiService.startUserDataStream(recvWindow, timestamp)).toString();
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, Long recvWindow, Long timestamp) {
        executeSync(bHexApiService.keepAliveUserDataStream(listenKey, recvWindow, timestamp));
    }

    @Override
    public void closeUserDataStream(String listenKey, Long recvWindow, Long timestamp) {
        executeSync(bHexApiService.closeAliveUserDataStream(listenKey, recvWindow, timestamp));
    }

}
