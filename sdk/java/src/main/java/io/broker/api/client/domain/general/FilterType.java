package io.broker.api.client.domain.general;


/**
 * Filters define trading rules on a symbol or an exchange. Filters come in two forms: symbol filters and exchange filters.
 */
public enum FilterType {
    PRICE_FILTER,
    LOT_SIZE,
    MIN_NOTIONAL,;
}
