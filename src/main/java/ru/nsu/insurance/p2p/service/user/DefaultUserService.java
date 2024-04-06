package ru.nsu.insurance.p2p.service.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.insurance.p2p.dao.UserRepository;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.UserRequest;
import ru.nsu.insurance.p2p.dto.response.UserResponse;
import ru.nsu.insurance.p2p.mapper.UserMapper;
import ru.nsu.insurance.p2p.service.exception.UserServiceException;
import ru.nsu.insurance.p2p.service.exception.existence.UserNotFoundException;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserProfile userToCreate = UserMapper.INSTANCE.userRequestToUserProfile(userRequest);
        try {
            return UserMapper.INSTANCE.userToUserResponse(userRepository.save(userToCreate));
        } catch (Exception exception) {
            throw new UserServiceException("There was a problem creating user", exception);
        }
    }

    @Override
    public UserResponse getUser(long userId) {
        UserProfile user = userRepository
            .findById(userId)
            .orElseThrow(UserNotFoundException::new);

        return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository
            .findAll()
            .stream()
            .map(UserMapper.INSTANCE::userToUserResponse)
            .toList();
    }

    @Override
    public UserResponse updateUser(long userId, UserRequest userRequest) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        UserProfile user = UserMapper.INSTANCE.userRequestToUserProfile(userRequest);
        user.setId(userId);

        try {
            return UserMapper.INSTANCE.userToUserResponse(userRepository.save(user));
        } catch (Exception exception) {
            throw new UserServiceException("There was a problem updating user", exception);
        }
    }

    @Override
    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        try {
            userRepository.deleteById(userId);
        } catch (Exception exception) {
            throw new UserServiceException("There was a problem deleting user", exception);
        }
    }
}
