package io.broker.api.client.service;

import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.contract.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface BrokerContractApiService {

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/contract/order")
    Call<ContractOrderResult> newContractOrder(@Query("symbol") String symbol,
                                               @Query("side") String side,
                                               @Query("orderType") String orderType,
                                               @Query("quantity") String quantity,
                                               @Query("leverage") String leverage,
                                               @Query("price") String price,
                                               @Query("priceType") String priceType,
                                               @Query("triggerPrice") String triggerPrice,
                                               @Query("timeInForce") String timeInForce,
                                               @Query("clientOrderId") String clientOrderId);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @DELETE("/openapi/contract/order/cancel")
    Call<ContractOrderResult> cancelContractOrder(@Query("orderId") Long orderId,
                                                  @Query("clientOrderId") String clientOrderId,
                                                  @Query("orderType") String orderType);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @DELETE("/openapi/contract/order/batchCancel")
    Call<BatchCancelOrderResult> batchCancelContractOrder(@Query("symbol") String symbol);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/contract/openOrders")
    Call<List<ContractOrderResult>> getContractOpenOrders(@Query("symbol") String symbol,
                                                          @Query("orderId") Long orderId,
                                                          @Query("side") String side,
                                                          @Query("orderType") String orderType,
                                                          @Query("limit") Integer limit);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/contract/historyOrders")
    Call<List<ContractOrderResult>> getContractHistoryOrders(@Query("symbol") String symbol,
                                                             @Query("orderId") Long orderId,
                                                             @Query("side") String side,
                                                             @Query("orderType") String orderType,
                                                             @Query("limit") Integer limit);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/contract/myTrades")
    Call<List<ContractMatchResult>> getContractMyTrades(@Query("symbol") String symbol,
                                                        @Query("limit") Integer limit,
                                                        @Query("side") String side,
                                                        @Query("fromId") Long fromId,
                                                        @Query("toId") Long toId);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/contract/positions")
    Call<List<ContractPositionResult>> getContractPositions(@Query("symbol") String symbol,
                                                            @Query("side") String side);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/contract/modifyMargin")
    Call<ModifyMarginResult> modifyMargin(@Query("symbol") String symbol,
                                          @Query("side") String side,
                                          @Query("amount") String amount);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/contract/account")
    Call<Map<String, ContractAccountResult>> getContractAccount();
}
