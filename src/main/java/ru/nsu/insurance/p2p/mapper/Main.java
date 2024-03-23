package ru.nsu.insurance.p2p.mapper;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.insurance.p2p.dao.entity.InsuranceContract;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.GroupRequest;
import ru.nsu.insurance.p2p.dto.response.ContractResponse;
import ru.nsu.insurance.p2p.dto.response.GroupResponse;
import ru.nsu.insurance.p2p.dto.response.GroupShortResponse;
import ru.nsu.insurance.p2p.dto.response.UserResponse;

public class Main {
    public static void main(String[] args) {
//        InsuranceContract contract = InsuranceContract.builder()
//            .address("contract-address")
//            .build();
//        System.out.println(contract);

//        ContractResponse contractResponse = ContractMapper.INSTANCE.contractToContractResponse(contract);
//        System.out.println(contractResponse);

        UserProfile user1 = UserProfile.builder()
            .id(1L)
            .username("aaaaaaaaa")
            .password("1111")
            .firstName("Alex")
            .surname("Zub")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .ethereumAddress("user-address")
            .groups(new ArrayList<>())
            .createdGroups(new ArrayList<>())
            .build();
        UserProfile user2 = UserProfile.builder()
            .id(2L)
            .username("bbbbbbbbbbbb")
            .password("1111")
            .firstName("Alex")
            .surname("Zub")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .ethereumAddress("user-address")
            .groups(new ArrayList<>())
            .createdGroups(new ArrayList<>())
            .build();

        UserResponse userResponse1 = UserMapper.INSTANCE.userToUserResponse(user1);
        System.out.println(userResponse1);

        System.out.println();

        InsuranceGroup group1 = InsuranceGroup.builder()
            .id(4L)
            .title("group-name")
            .description("some description")
            .createdAt(OffsetDateTime.now())
//            .contract(contract)
            .createdBy(user1)
            .users(List.of(user1, user2))
            .build();

        user1.getGroups().add(group1);
        user1.getCreatedGroups().add(group1);
        user2.getGroups().add(group1);

        System.out.println(group1);
        System.out.println();
        System.out.println(user1);
        System.out.println(user2);
        System.out.println();

        GroupResponse groupResponse = GroupMapper.INSTANCE.groupToGroupResponse(group1);
        System.out.println(groupResponse);

        GroupShortResponse groupShortResponse = GroupMapper.INSTANCE.groupToGroupShortResponse(group1);
        System.out.println(groupShortResponse);

        System.out.println();

//        GroupRequest groupRequest = new GroupRequest(
//            "title",
//            "description",
//            List.of(1L, 2L, 3L)
//        );
//        System.out.println(groupRequest);
//
//        InsuranceGroup group2 = GroupMapper.INSTANCE.groupRequestToInsuranceGroup(groupRequest);
//        System.out.println(group2);


        UserResponse userResponse3 = UserMapper.INSTANCE.userToUserResponse(user1);
        System.out.println(userResponse3);

        for (var group : user1.getGroups()) {
            System.out.println(GroupMapper.INSTANCE.groupToGroupResponse(group));
        }

//        System.out.println(GroupMapper.INSTANCE.groupToGroupResponse(user1.getGroups()));
    }
}
