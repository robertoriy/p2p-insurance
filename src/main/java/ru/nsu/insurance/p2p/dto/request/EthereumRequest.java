package ru.nsu.insurance.p2p.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EthereumRequest(
    @JsonProperty("private_key") String privateKey
) {
}
