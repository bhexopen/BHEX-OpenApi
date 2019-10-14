package io.broker.api.client.domain.option.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OptionsRequest {

    private Boolean expired;

    private Long recvWindow;

    private Long timestamp;

}
