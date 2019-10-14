package io.broker.api.client;

import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.*;

import java.util.List;
import java.util.Map;

public interface BrokerContractApiRestClient {

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
}
