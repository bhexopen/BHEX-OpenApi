package io.broker.api.client;

import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.*;
import io.broker.api.client.domain.general.BrokerInfo;
import io.broker.api.client.domain.general.TradeType;
import io.broker.api.client.domain.market.Candlestick;
import io.broker.api.client.domain.market.CandlestickInterval;
import io.broker.api.client.domain.market.OrderBook;
import io.broker.api.client.domain.market.TradeHistoryItem;

import java.util.List;
import java.util.Map;

public interface BrokerContractApiRestClient {

    /**
     * Trading section information.
     * Possible values include 'token', 'options', and 'contracts'. 
     * If the parameter is not sent, all trading information will be returned.
     * 
     * @param type broker trade type
     * @return broker info
     */
    BrokerInfo getBrokerInfo(TradeType type);

    /**
     * Send in a new contract order.
     *
     * @param request the new order to submit.
     * @return a response containing details about the newly placed order.
     */
    ContractOrderResult newContractOrder(ContractOrderRequest request);

    /**
     * Cancel an active contract order.
     *
     * @param cancelOrderRequest order status request parameters
     * @return order result
     */
    ContractOrderResult cancelContractOrder(CancelContractOrderRequest cancelOrderRequest);

    /**
     * Batch cancel active contract orders.
     *
     * @param request batch cancel order request parameters
     * @return batch cancel result
     */
    BatchCancelOrderResult batchCancelContractOrder(BatchCancelOrderRequest request);

    /**
     * Get contract order detail.
     * include LIMIT and STOP orders
     *
     * @param orderType the order type.
     * @param clientOrderId client unique id.
     * @param orderId order id.
     * @return order detail
     */
    ContractOrderResult getContractOrder(OrderType orderType, String clientOrderId, Long orderId);

    /**
     * Get contract open orders.
     *
     * @param orderRequest open order request parameters
     * @return a list of all account open orders on a symbol.
     */
    List<ContractOrderResult> getContractOpenOrders(ContractOpenOrderRequest orderRequest);

    /**
     * Get contract history orders.
     *
     * @param orderRequest history order request parameters
     * @return history orders
     */
    List<ContractOrderResult> getContractHistoryOrders(ContractHistoryOrderRequest orderRequest);

    /**
     * Get contract my trades.
     *
     * @param request trade request parameters
     * @return trades
     */
    List<ContractMatchResult> getContractMyTrades(ContractMyTradeRequest request);

    /**
     * Get contract positions.
     *
     * @param request position request parameters
     * @return positions
     */
    List<ContractPositionResult> getContractPositions(ContractPositionRequest request);


    /**
     * Modify contract position margin.
     *
     * @param request modify margin request parameters
     * @return position margin.
     */
    ModifyMarginResult modifyMargin(ModifyMarginRequest request);

    /**
     * Get contract account info.
     *
     * @return account info.
     */
    Map<String, ContractAccountResult> getContractAccount();

    /**
     * * Get contract's order book of a symbol.
     *
     * @param symbol ticker symbol (e.g. ETHBTC)
     * @param limit  depth of the order book (max 100)
     * @return a `OrderBook` instance
     */
    OrderBook getContractOrderBook(String symbol, Integer limit);

    /**
     * Get contract recent trades (up to last 500). Weight: 1
     *
     * @param symbol The name of the contract
     * @param limit  The number of trades returned
     * @return trade list           
     */
    List<TradeHistoryItem> getContractTrades(String symbol, Integer limit);

    /**
     * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
     * @param symbol Name of the contract
     * @param interval Interval of the kline
     * @param limit Number of entries returned. Max is capped at 1000
     * @param to timestamp of the last datapoint
     * @return Kline/candlestick bars
     */
    List<Candlestick> getContractCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long to);
}
