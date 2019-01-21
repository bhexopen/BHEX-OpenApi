package io.bhex.api.client;

import io.bhex.api.client.constant.BHexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BHex API error object.
 */
public class BHexApiError {

    /**
     * Error code.
     */
    private int code;

    /**
     * Error message.
     */
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BHexConstants.TO_STRING_BUILDER_STYLE)
                .append("code", code)
                .append("msg", msg)
                .toString();
    }
}
