package ru.nsu.insurance.p2p.service.group;

import ru.nsu.insurance.p2p.dto.request.GroupRequest;
import ru.nsu.insurance.p2p.dto.response.GroupResponse;
import java.util.List;

public interface GroupService {
    GroupResponse createGroup(long requesterId, GroupRequest groupRequest);

    GroupResponse getGroup(long requesterId, long groupId);

    List<GroupResponse> getGroupsByUserId(long userId);

    GroupResponse updateGroup(long requesterId, long groupId, GroupRequest groupRequest);

    void deleteGroup(long requesterId, long groupId);
}
