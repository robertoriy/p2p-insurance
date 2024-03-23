package ru.nsu.insurance.p2p.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.UserRequest;
import ru.nsu.insurance.p2p.dto.response.UserResponse;
import ru.nsu.insurance.p2p.dto.response.UserShortResponse;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdGroups", ignore = true)
    @Mapping(target = "groups", ignore = true)
    UserProfile userRequestToUserProfile(UserRequest request);

    @Mapping(target = "userId", source = "id")
    UserShortResponse userToUserShortResponse(UserProfile user);

    default UserResponse userToUserResponse(UserProfile user) {
        List<InsuranceGroup> created = user.getCreatedGroups() == null ? new ArrayList<>() : user.getCreatedGroups();
        List<InsuranceGroup> groups = user.getGroups() == null ? new ArrayList<>() : user.getGroups();
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getFirstName(),
            user.getSurname(),
            user.getDateOfBirth(),
            user.getEthereumAddress(),
            created.stream()
                .map(GroupMapper.INSTANCE::groupToGroupShortResponse)
                .toList(),
            groups.stream()
                .map(GroupMapper.INSTANCE::groupToGroupShortResponse)
                .toList()
        );
    }
}
