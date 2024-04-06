package ru.nsu.insurance.p2p.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "credentials",
    "contribute_eth_value"
})
public record ContractDeployRequest(
    @JsonProperty("credentials") EthereumRequest credentials,
    @JsonProperty("contribute_eth_value") long contributeEthValue
) {
}
