package io.bhex.api.client.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocketUserResponse {

    private Long pingTime;

    private List<SocketOrder> orderList;

    private List<SocketAccount> accountList;

}
