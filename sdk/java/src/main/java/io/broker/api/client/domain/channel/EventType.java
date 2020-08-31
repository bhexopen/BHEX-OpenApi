package io.broker.api.client.domain.channel;

public enum EventType {

    SUB("sub"),
    CANCEL("cancel"),
    CANCEL_ALL("cancel_all"),
    ACCOUNT_INFO("outboundAccountInfo"),
    MARGIN_ACCOUNT_INFO("outboundMarginAccountInfo"),
    EXECUTION_REPORT("executionReport"),
    OPTION_EXECUTION_REPORT("optionExecutionReport"),
    CONTRACT_EXECUTION_REPORT("contractExecutionReport"),
    MARGIN_EXECUTION_REPORT("marginExecutionReport");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
