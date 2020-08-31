package io.broker.api.client;

import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.account.request.*;
import io.broker.api.client.domain.margin.*;
import io.broker.api.client.domain.margin.request.*;

import java.util.List;

public interface BrokerMarginApiRestClient {
    /**
     * Send in a new order.
     *
     * @param order the new order to submit.
     * @return a response containing details about the newly placed order.
     */
    NewOrderResponse newOrder(NewOrder order);

    /**
     * Check an order's status.
     *
     * @param orderStatusRequest order status request options/filters
     * @return an order
     */
    Order getOrderStatus(OrderStatusRequest orderStatusRequest);

    /**
     * Cancel an active order.
     *
     * @param cancelOrderRequest order status request parameters
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest);

    /**
     * Get all open orders on a symbol.
     *
     * @param orderRequest order request parameters
     * @return a list of all account open orders on a symbol.
     */
    List<Order> getOpenOrders(OpenOrderRequest orderRequest);

    /**
     * Get all account orders; active, canceled, or filled.
     *
     * @param orderRequest order request parameters
     * @return a list of all account orders
     */
    List<Order> getHistoryOrders(HistoryOrderRequest orderRequest);

    /**
     * Get current account information.
     */
    Account getAccount(Long recvWindow, Long timestamp);

    /**
     * Get trades for a specific account and symbol.
     *
     * @param request
     * @return
     */
    List<Trade> getMyTrades(MyTradeRequest request);

    /**
     * open margin account
     *
     * @param recvWindow
     * @param timestamp
     * @return
     */
    MarginBaseResponse openMargin(Long recvWindow, Long timestamp);

    /**
     * get margin safety
     *
     * @param recvWindow
     * @param timestamp
     * @return
     */
    MarginSafetyResponse getMarginSafety(Long recvWindow, Long timestamp);

    List<MarginToken> queryMarginToken();

    MarginRiskConfigResponse getMarginRiskConfig();

    MarginLoanableResponse getMarginLoanable(MarginLoanableRequest request);

    List<MarginLoanPosition> queryLoanPosition(MarginLoanPositionRequest request);

    MarginAvailWithdrawResponse getAvailWithdraw(MarginAvailWithdrawRequest request);

    MarginBaseResponse marginTransfer(MarginTransferRequest request);

    LoanResponse loan(LoanRequest request);

    RepayResponse repay(RepayRequest request);

    MarginAllPositionResponse queryAllPosition(Long recvWindow, Long timestamp);

    List<LoanOrder> queryLoanOrders(QueryLoanOdersRequest request);

    LoanOrder getLoanOrder(Long loanOrderId, Long recvWindow, Long timestamp);

    List<RepayOrder> queryRepayOrders(QueryRepayOrdersRequest request);

    List<RepayOrder> queryRepayOrderByLoanId(QueryRepayOrdersRequest request);

    List<MarginBalanceFlow> queryBalaceFlow(GetMarginBalanceFlowRequest request);

}
