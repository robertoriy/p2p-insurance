package ru.nsu.insurance.p2p.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;
import java.util.List;

@JsonPropertyOrder({
    "user_id",
    "username",
    "password",
    "first_name",
    "surname",
    "date_of_birth",
    "ethereum_address",
    "created_groups",
    "groups"
})
public record UserResponse(
    @JsonProperty("user_id") long userId,
    @JsonProperty("username") String username,
    @JsonProperty("password") String password,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("surname") String surname,
    @JsonProperty("date_of_birth") LocalDate dateOfBirth,
    @JsonProperty("ethereum_address") String ethereumAddress,
    @JsonProperty("created_groups") List<GroupShortResponse> createdGroups,
    @JsonProperty("groups") List<GroupShortResponse> groups
) {
}
