package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An asset balance in an Account.
 *
 * @see Account
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetBalance {

    /**
     * Asset symbol.
     */
    private String asset;

    /**
     * Available balance.
     */
    private String free;

    /**
     * Locked by open orders.
     */
    private String locked;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("asset", asset)
                .append("free", free)
                .append("locked", locked)
                .toString();
    }
}
