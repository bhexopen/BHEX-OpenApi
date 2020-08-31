package io.broker.api.client.service;

import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.account.*;
import io.broker.api.client.domain.margin.*;
import retrofit2.Call;
import retrofit2.http.*;

import javax.swing.*;
import java.util.List;

public interface BrokerMarginApiService {

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/v1/margin/order")
    Call<NewOrderResponse> newOrder(@Query("symbol") String symbol,
                                    @Query("side") OrderSide side,
                                    @Query("type") OrderType type,
                                    @Query("quantity") String quantity,
                                    @Query("price") String price,
                                    @Query("newClientOrderId") String newClientOrderId,
                                    @Query("recvWindow") Long recvWindow,
                                    @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/order")
    Call<Order> getOrderStatus(@Query("orderId") Long orderId,
                               @Query("origClientOrderId") String origClientOrderId,
                               @Query("recvWindow") Long recvWindow,
                               @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @DELETE("/openapi/v1/margin/order")
    Call<CancelOrderResponse> cancelOrder(@Query("orderId") Long orderId,
                                          @Query("clientOrderId") String clientOrderId,
                                          @Query("recvWindow") Long recvWindow,
                                          @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/openOrders")
    Call<List<Order>> getOpenOrders(@Query("symbol") String symbol,
                                    @Query("limit") Integer limit,
                                    @Query("recvWindow") Long recvWindow,
                                    @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/historyOrders")
    Call<List<Order>> getHistroyOrders(@Query("orderId") Long orderId,
                                       @Query("startTime") Long startTime,
                                       @Query("endTime") Long endTime,
                                       @Query("limit") Integer limit,
                                       @Query("recvWindow") Long recvWindow,
                                       @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/account")
    Call<Account> getAccount(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/myTrades")
    Call<List<Trade>> getMyTrades(@Query("fromId") Long fromId,
                                  @Query("toId") Long toId,
                                  @Query("startTime") Long startTime,
                                  @Query("endTime") Long endTime,
                                  @Query("limit") Integer limit,
                                  @Query("recvWindow") Long recvWindow,
                                  @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/v1/margin/open")
    Call<MarginBaseResponse> openMargin(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/v1/margin/safety")
    Call<MarginSafetyResponse> getMarginSafety(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/token")
    Call<List<MarginToken>> queryMarginToken();

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/riskConfig")
    Call<MarginRiskConfigResponse> getMarginRisk();

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/loanable")
    Call<MarginLoanableResponse> getMarginLoanable(@Query("tokenId") String tokenId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/loanPosition")
    Call<List<MarginLoanPosition>> queryLoanPosition(@Query("tokenId") String tokenId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/availWithdraw")
    Call<MarginAvailWithdrawResponse> getAvailWithdraw(@Query("tokenId") String tokenId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/transfer")
    Call<MarginBaseResponse> marginTransfer(@Query("fromAccountType") Integer fromAccountType,
                                            @Query("toAccountType") Integer toAccountType,
                                            @Query("tokenId") String tokenId,
                                            @Query("amount") String amount,
                                            @Query("recvWindow") Long recvWindow,
                                            @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/v1/margin/loan")
    Call<LoanResponse> loan(@Query("clientOrderId") String clientOrderId,
                            @Query("loanAmount") String loanAmount,
                            @Query("tokenId") String tokenId,
                            @Query("recvWindow") Long recvWindow,
                            @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/openapi/v1/margin/repay")
    Call<RepayResponse> repay(@Query("clientOrderId") String clientOrderId,
                              @Query("loanOrderId") Long loanOrderId,
                              @Query("repayType") Integer repayType,
                              @Query("repayAmount") String repayAmount,
                              @Query("recvWindow") Long recvWindow,
                              @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/allPosition")
    Call<MarginAllPositionResponse> queryAllPosition(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/loanOrders")
    Call<List<LoanOrder>> queryLoanOrders(@Query("tokenId") String tokenId,
                                          @Query("status") Integer status,
                                          @Query("fromLoanId") Long fromLoanId,
                                          @Query("endLoanId") Long endLoanId,
                                          @Query("limit") Integer limit,
                                          @Query("recvWindow") Long recvWindow,
                                          @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/getLoanOrder")
    Call<LoanOrder> getLoanOrder(@Query("loanOrderId") Long loanOrderId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/repayOrders")
    Call<List<RepayOrder>> queryRepayOrders(@Query("tokenId")String tokenId,
                                            @Query("fromRepayId") Long fromRepayId,
                                            @Query("endRepayId")Long endRepayId,
                                            @Query("limit") Integer limit,
                                            @Query("recvWindow") Long recvWindow,
                                            @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/getRepayOrder")
    Call<List<RepayOrder>> getRepayOrderByLoanId(@Query("loanOrderId")Long loanOrderId,
                                                 @Query("fromRepayId") Long fromRepayId,
                                                 @Query("endRepayId")Long endRepayId,
                                                 @Query("limit") Integer limit,
                                                 @Query("recvWindow") Long recvWindow,
                                                 @Query("timestamp") Long timestamp);

    @Headers(BrokerConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/openapi/v1/margin/balanceFlow")
    Call<List<MarginBalanceFlow>> queryBalanceFlow(@Query("tokenId")String tokenId,
                                                   @Query("fromFlowId")Long fromFlowId,
                                                   @Query("endFlowId")Long endFlowId,
                                                   @Query("startTime")Long startTime,
                                                   @Query("endTime")Long endTime,
                                                   @Query("limit") Integer limit,
                                                   @Query("recvWindow") Long recvWindow,
                                                   @Query("timestamp") Long timestamp);

}
