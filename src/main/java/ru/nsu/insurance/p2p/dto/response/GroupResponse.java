package ru.nsu.insurance.p2p.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.OffsetDateTime;
import java.util.List;

@JsonPropertyOrder({
    "group_id",
    "title",
    "description",
    "created_at",
    "contract",
    "created_by",
    "users"
})
public record GroupResponse(
    @JsonProperty("group_id") long groupId,
    @JsonProperty("title") String title,
    @JsonProperty("description") String description,
    @JsonProperty("created_at") OffsetDateTime createdAt,
    @JsonProperty("contract") ContractResponse contract,
    @JsonProperty("created_by") UserShortResponse createdBy,
    @JsonProperty("users") List<UserShortResponse> users
) {
}
