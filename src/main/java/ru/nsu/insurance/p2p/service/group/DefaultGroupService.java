package ru.nsu.insurance.p2p.service.group;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.insurance.p2p.dao.GroupRepository;
import ru.nsu.insurance.p2p.dao.UserRepository;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.GroupRequest;
import ru.nsu.insurance.p2p.dto.response.GroupResponse;
import ru.nsu.insurance.p2p.mapper.GroupMapper;
import ru.nsu.insurance.p2p.service.exception.GroupServiceException;
import ru.nsu.insurance.p2p.service.exception.InsuranceAccessDeniedException;
import ru.nsu.insurance.p2p.service.exception.existence.GroupNotFoundException;
import ru.nsu.insurance.p2p.service.exception.existence.UserNotFoundException;

@RequiredArgsConstructor
@Service
public class DefaultGroupService implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public GroupResponse createGroup(long requesterId, GroupRequest groupRequest) {
        List<Long> userIds = groupRequest.users();

        checkPermissions(requesterId, userIds);

        List<UserProfile> users = new ArrayList<>();

        for (Long userId : userIds) {
            userRepository.findById(userId).ifPresentOrElse(users::add, UserNotFoundException::new);
        }

        InsuranceGroup group = GroupMapper.INSTANCE.groupRequestToInsuranceGroup(groupRequest);

        userRepository.findById(requesterId).ifPresentOrElse(group::setCreatedBy, UserNotFoundException::new);
        group.setCreatedAt(OffsetDateTime.now());
        group.setUsers(users);
        for (UserProfile user : users) {
            user.getGroups().add(group);
        }

        try {
            InsuranceGroup savedGroup = groupRepository.save(group);
            return GroupMapper.INSTANCE.groupToGroupResponse(savedGroup);
        } catch (Exception exception) {
            throw new GroupServiceException("Failed to create the group", exception);
        }
    }

    @Override
    public GroupResponse getGroup(long requesterId, long groupId) {
        if (!userRepository.existsById(requesterId)) {
            throw new UserNotFoundException();
        }
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        return GroupMapper.INSTANCE.groupToGroupResponse(group);
    }

    @Override
    public List<GroupResponse> getGroupsByUserId(long userId) {
        return List.of();
    }

    @Override
    public GroupResponse updateGroup(long requesterId, long groupId, GroupRequest groupRequest) {
        return null;
    }

    @Override
    public void deleteGroup(long requesterId, long groupId) {

    }

    private void checkPermissions(long requesterId, List<Long> userIds) {
        if (!userIds.contains(requesterId)) {
            throw new InsuranceAccessDeniedException();
        }
    }
}
