package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositOrder {

    /**
     * deposit order time
     */
    private Long time;

    /**
     * deposit order id
     */
    private Long orderId;

    /**
     * deposit token
     */
    private String token;

    /**
     * deposit in address
     */
    private String address;

    /**
     * deposit in address tag (for eos)
     */
    private String addressTag;

    /**
     * deposit from address
     */
    private String fromAddress;

    /**
     * deposit from address tag (for eos)
     */
    private String fromAddressTag;

    /**
     * deposit quantity
     */
    private String quantity;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromAddressTag() {
        return fromAddressTag;
    }

    public void setFromAddressTag(String fromAddressTag) {
        this.fromAddressTag = fromAddressTag;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("token", token)
                .append("time", time)
                .append("orderId", orderId)
                .append("address", address)
                .append("addressTag", addressTag)
                .append("fromAddress", fromAddress)
                .append("fromAddressTag", fromAddressTag)
                .append("quantity", quantity)
                .toString();
    }
}
