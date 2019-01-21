package io.bhex.api.client.domain.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.bhex.api.client.constant.BHexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokerInfo {

    private String timezone;

    private Long serverTime;

    private List brokerFilters;

    private List<Symbol> symbols;

    private List<RateLimit> rateLimits;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public List getBrokerFilters() {
        return brokerFilters;
    }

    public void setBrokerFilters(List brokerFilters) {
        this.brokerFilters = brokerFilters;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<RateLimit> getRateLimits() {
        return rateLimits;
    }

    public void setRateLimits(List<RateLimit> rateLimits) {
        this.rateLimits = rateLimits;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BHexConstants.TO_STRING_BUILDER_STYLE)
                .append("timezone", timezone)
                .append("serverTime", serverTime)
                .append("brokerFilters", brokerFilters)
                .append("rateLimits", rateLimits)
                .append("symbols", symbols)
                .toString();
    }

}
