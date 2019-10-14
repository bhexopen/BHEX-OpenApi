package io.broker.api.client.domain.channel;

public enum EventType {

    SUB("sub"),
    CANCEL("cancel"),
    CANCEL_ALL("cancel_all"),
    ACCOUNT_INFO("outboundAccountInfo"),
    EXECUTION_REPORT("executionReport"),;

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
