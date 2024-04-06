package ru.nsu.insurance.p2p.service.user;

import java.util.List;
import ru.nsu.insurance.p2p.dto.request.UserRequest;
import ru.nsu.insurance.p2p.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUser(long userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(long userId, UserRequest userRequest);

    void deleteUser(long userId);
}
