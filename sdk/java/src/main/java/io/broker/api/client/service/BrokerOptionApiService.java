package io.broker.api.client.service;

import java.util.List;

import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.option.OptionMatchResult;
import io.broker.api.client.domain.option.OptionOrderResult;
import io.broker.api.client.domain.option.OrderResult;
import io.broker.api.client.domain.option.PositionResult;
import io.broker.api.client.domain.option.SettlementResult;
import io.broker.api.client.domain.option.TokenOptionResult;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Broker's Option REST API URL mappings and endpoint security configuration.
 */
public interface BrokerOptionApiService {

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/getOptions")
    Call<List<TokenOptionResult>> getOptions(@Query("expired") Boolean expired);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/option/v1/order")
    Call<OptionOrderResult> newOptionOrder(@Query("symbol") String symbol,
                                           @Query("side") String side,
                                           @Query("type") String type,
                                           @Query("timeInForce") String timeInForce,
                                           @Query("quantity") String quantity,
                                           @Query("price") String price,
                                           @Query("clientOrderId") String clientOrderId,
                                           @Query("recvWindow") Long recvWindow,
                                           @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @DELETE("/openapi/option/v1/order/cancel")
    Call<OptionOrderResult> cancelOptionOrder(@Query("orderId") Long orderId,
                                              @Query("clientOrderId") String clientOrderId,
                                              @Query("recvWindow") Long recvWindow,
                                              @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/openOrders")
    Call<List<OptionOrderResult>> getOptionOpenOrders(@Query("symbol") String symbol,
                                                      @Query("orderId") Long orderId,
                                                      @Query("limit") Integer limit,
                                                      @Query("side") String side,
                                                      @Query("type") String type,
                                                      @Query("recvWindow") Long recvWindow,
                                                      @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/historyOrders")
    Call<List<OptionOrderResult>> getOptionHistoryOrders(@Query("symbol") String symbol,
                                                         @Query("side") String side,
                                                         @Query("type") String type,
                                                         @Query("limit") Integer limit,
                                                         @Query("orderStatus") String orderStatus,
                                                         @Query("recvWindow") Long recvWindow,
                                                         @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/myTrades")
    Call<List<OptionMatchResult>> getOptionMyTrades(@Query("symbol") String symbol,
                                                    @Query("fromId") Long fromId,
                                                    @Query("toId") Long toId,
                                                    @Query("limit") Integer limit,
                                                    @Query("side") String side,
                                                    @Query("recvWindow") Long recvWindow,
                                                    @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/positions")
    Call<List<PositionResult>> getOptionPositions(@Query("symbol") String symbol,
                                                  @Query("recvWindow") Long recvWindow,
                                                  @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/settlements")
    Call<List<SettlementResult>> getOptionSettlements(@Query("symbol") String symbol,
                                                      @Query("recvWindow") Long recvWindow,
                                                      @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/getOrder")
    Call<OrderResult> getOrder(@Query("orderId") Long orderId,
                               @Query("clientOrderId") Long clientOrderId,
                               @Query("recvWindow") Long recvWindow,
                               @Query("timestamp") Long timestamp);
}
