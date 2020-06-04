package io.broker.api.client.domain.option;

import java.util.List;

import lombok.Singular;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020-06-04 11:10
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
public class OrderResult {

    /**
     * Time.
     */
    private Long time; //时间

    /**
     * Update time.
     */
    private Long updateTime;

    /**
     * Order id.
     */
    private Long orderId; // 订单ID

    /**
     * Account id.
     */
    private Long accountId; //账户ID

    /**
     * Client order id.
     */
    private String clientOrderId;

    /**
     * Symbol.
     */
    private String symbol; //币对Id

    /**
     * price.
     */
    private String price; //下单价格

    /**
     * Orig qty.
     */
    private String origQty; //原始下单数量

    /**
     * Executed qty.
     */
    private String executedQty; //成交量

    /**
     * Executed amount.
     */
    private String executedAmount; //成交量

    /**
     * Avg price.
     */
    private String avgPrice; // 成交均价

    /**
     * Type.
     */
    private String type; //订单类型

    /**
     * Side.
     */
    private String side; //买卖方向

    /**
     * Status.
     */
    private String status; //状态标识


    private String option;
    private String timeInForce;
    private @Singular("fee")
    List<OrderMatchFeeInfo> fees;
}
