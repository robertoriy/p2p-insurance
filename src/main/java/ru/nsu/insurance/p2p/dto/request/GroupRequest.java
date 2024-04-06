package ru.nsu.insurance.p2p.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({
    "title",
    "description",
    "users"
})
public record GroupRequest(
    @JsonProperty("title") String title,
    @JsonProperty("description") String description,
    @JsonProperty("users") List<Long> users
) {
}
