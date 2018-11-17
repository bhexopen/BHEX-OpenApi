package io.bhex.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocketOrder {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("c")
    private String clientOrderId;

    @JsonProperty("S")
    private String orderSide;

    @JsonProperty("o")
    private String orderType;

    @JsonProperty("f")
    private String timeInForce;

    @JsonProperty("q")
    private String quantity;

    @JsonProperty("p")
    private String price;

    @JsonProperty("X")
    private String status;

    @JsonProperty("i")
    private Long orderId;

    @JsonProperty("z")
    private String executedQuantity;


    @JsonProperty("w")
    private Boolean isWorking;

    @JsonProperty("m")
    private Boolean isMaker;
//
//    private Boolean M;

    @JsonProperty("O")
    private Long createTime;

    @JsonProperty("Z")
    private String executedAmount;


    public static void main(String[] args) {

        String str = "{\"e\":\"executionReport\",\"E\":\"1542090718548\",\"s\":\"BTCUSDT\",\"c\":\"1542090639684\",\"S\":\"BUY\",\"o\":\"LIMIT\",\"f\":\"GTC\",\"q\":\"1\",\"p\":\"99\",\"X\":\"CANCELED\",\"i\":\"229467349586154752\",\"z\":\"0\",\"w\":true,\"m\":false,\"O\":\"1542090639720\",\"Z\":\"0\"}";

        ObjectMapper objectMapper =new  ObjectMapper();
        try {
            SocketOrder order = objectMapper.readValue(str,SocketOrder.class);
            System.out.println(order);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
}
