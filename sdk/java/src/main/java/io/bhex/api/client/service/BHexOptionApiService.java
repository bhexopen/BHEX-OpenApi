package io.bhex.api.client.service;

import java.util.List;

import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.client.domain.account.OrderSide;
import io.bhex.api.client.domain.account.OrderStatus;
import io.bhex.api.client.domain.account.OrderType;
import io.bhex.api.client.domain.account.TimeInForce;
import io.bhex.api.client.domain.option.OptionMatchResult;
import io.bhex.api.client.domain.option.OptionOrderResult;
import io.bhex.api.client.domain.option.PositionResult;
import io.bhex.api.client.domain.option.SettlementResult;
import io.bhex.api.client.domain.option.TokenOptionResult;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * BHex's Option REST API URL mappings and endpoint security configuration.
 */
public interface BHexOptionApiService {

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/getOptions")
    Call<List<TokenOptionResult>> getOptions(@Query("expired") Boolean expired);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
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

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @DELETE("/openapi/option/v1/order/cancel")
    Call<OptionOrderResult> cancelOptionOrder(@Query("orderId") Long orderId,
                                              @Query("clientOrderId") String clientOrderId,
                                              @Query("recvWindow") Long recvWindow,
                                              @Query("timestamp") Long timestamp);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/openOrders")
    Call<List<OptionOrderResult>> getOptionOpenOrders(@Query("symbol") String symbol,
                                                      @Query("orderId") Long orderId,
                                                      @Query("limit") Integer limit,
                                                      @Query("side") String side,
                                                      @Query("type") String type,
                                                      @Query("recvWindow") Long recvWindow,
                                                      @Query("timestamp") Long timestamp);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/historyOrders")
    Call<List<OptionOrderResult>> getOptionHistoryOrders(@Query("symbol") String symbol,
                                                         @Query("side") String side,
                                                         @Query("type") String type,
                                                         @Query("limit") Integer limit,
                                                         @Query("orderStatus") String orderStatus,
                                                         @Query("recvWindow") Long recvWindow,
                                                         @Query("timestamp") Long timestamp);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/myTrades")
    Call<List<OptionMatchResult>> getOptionMyTrades(@Query("symbol") String symbol,
                                                    @Query("fromId") Long fromId,
                                                    @Query("toId") Long toId,
                                                    @Query("limit") Integer limit,
                                                    @Query("side") String side,
                                                    @Query("recvWindow") Long recvWindow,
                                                    @Query("timestamp") Long timestamp);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/positions")
    Call<List<PositionResult>> getOptionPositions(@Query("symbol") String symbol,
                                                  @Query("recvWindow") Long recvWindow,
                                                  @Query("timestamp") Long timestamp);

    @Headers(BHexConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/option/v1/settlements")
    Call<List<SettlementResult>> getOptionSettlements(@Query("symbol") String symbol,
                                                      @Query("recvWindow") Long recvWindow,
                                                      @Query("timestamp") Long timestamp);
}
