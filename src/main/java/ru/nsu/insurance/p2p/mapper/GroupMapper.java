package ru.nsu.insurance.p2p.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.GroupRequest;
import ru.nsu.insurance.p2p.dto.response.GroupResponse;
import ru.nsu.insurance.p2p.dto.response.GroupShortResponse;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "users", ignore = true)
    InsuranceGroup groupRequestToInsuranceGroup(GroupRequest request);

    default GroupShortResponse groupToGroupShortResponse(InsuranceGroup group) {
        return new GroupShortResponse(
            group.getId(),
            group.getTitle(),
            group.getDescription(),
            group.getCreatedAt(),
            ContractMapper.INSTANCE.contractToContractResponse(group.getContract()),
            UserMapper.INSTANCE.userToUserShortResponse(group.getCreatedBy())
        );
    }

    default GroupResponse groupToGroupResponse(InsuranceGroup group) {
        List<UserProfile> users = group.getUsers() == null ? new ArrayList<>() : group.getUsers();
        return new GroupResponse(
            group.getId(),
            group.getTitle(),
            group.getDescription(),
            group.getCreatedAt(),
            ContractMapper.INSTANCE.contractToContractResponse(group.getContract()),
            UserMapper.INSTANCE.userToUserShortResponse(group.getCreatedBy()),
            users.stream()
                .map(UserMapper.INSTANCE::userToUserShortResponse)
                .toList()
        );
    }
}
