package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocketAccount {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("m")
    private String makerCommission;

    @JsonProperty("t")
    private String takerCommission;

    @JsonProperty("b")
    private String buyerCommission;

    @JsonProperty("s")
    private String sellerCommission;

    @JsonProperty("T")
    private Boolean canTrade;

    @JsonProperty("W")
    private Boolean canWithdraw;

    @JsonProperty("D")
    private Boolean canDeposit;

    @JsonProperty("u")
    private Long lastUpdated;

    @JsonProperty("B")
    private List<SocketBalance> balanceChangedList;

}
