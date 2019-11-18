package io.broker.api.client.domain.channel;

public enum EventType {

    SUB("sub"),
    CANCEL("cancel"),
    CANCEL_ALL("cancel_all"),
    ACCOUNT_INFO("outboundAccountInfo"),
    EXECUTION_REPORT("executionReport"),
    OPTION_EXECUTION_REPORT("optionExecutionReport"),
    CONTRACT_EXECUTION_REPORT("contractExecutionReport");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
