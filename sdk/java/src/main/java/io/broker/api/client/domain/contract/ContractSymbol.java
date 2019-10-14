package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.broker.api.client.domain.general.SymbolFilter;
import io.broker.api.client.domain.general.SymbolStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractSymbol implements Serializable {

    private List<SymbolFilter> filters;

    private String exchangeId;
    private String symbol;
    private String symbolName;
    private SymbolStatus status;
    private String baseAsset;
    private String baseAssetPrecision;
    private String quoteAsset;
    private String quoteAssetPrecision;
    private boolean icebergAllowed;
    private boolean inverse;
    private String index;
    private String marginToken;
    private String marginPrecision;
    private String contractMultiplier;
    private List<RiskLimit> riskLimits;
}
