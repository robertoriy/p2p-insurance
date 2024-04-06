package ru.nsu.insurance.p2p.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "contract_id",
    "ethereum_address",
    "contribute_value",
})
public record ContractResponse(
    @JsonProperty("contract_id") long contractId,
    @JsonProperty("ethereum_address") String ethereumAddress,
    @JsonProperty("contribute_value") long contributeValue
) {
}
