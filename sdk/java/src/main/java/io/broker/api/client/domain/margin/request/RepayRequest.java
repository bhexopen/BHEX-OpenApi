package io.broker.api.client.domain.margin.request;

public class RepayRequest extends MarginBaseRequest {

    private String clientOrderId;
    private Long loanOrderId;
    private int repayType;
    private String repayAmount;

    public RepayRequest(Long loanOrderId, int repayType) {
        this.loanOrderId = loanOrderId;
        this.repayType = repayType;
    }

    public RepayRequest(Long loanOrderId, int repayType, String repayAmount) {
        this.loanOrderId = loanOrderId;
        this.repayType = repayType;
        this.repayAmount = repayAmount;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public RepayRequest clientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    public Long getLoanOrderId() {
        return loanOrderId;
    }

    public RepayRequest loanOrderId(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
        return this;
    }

    public int getRepayType() {
        return repayType;
    }

    public RepayRequest repayType(int repayType) {
        this.repayType = repayType;
        return this;
    }

    public String getRepayAmount() {
        return repayAmount;
    }

    public RepayRequest repayAmount(String repayAmount) {
        this.repayAmount = repayAmount;
        return this;
    }
}
