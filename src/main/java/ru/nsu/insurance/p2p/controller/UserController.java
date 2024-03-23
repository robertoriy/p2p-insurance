package ru.nsu.insurance.p2p.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.insurance.p2p.dto.request.UserRequest;
import ru.nsu.insurance.p2p.dto.response.UserResponse;
import ru.nsu.insurance.p2p.service.exception.UserServiceException;
import ru.nsu.insurance.p2p.service.exception.existence.NotFoundException;
import ru.nsu.insurance.p2p.service.user.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/")
    public ResponseEntity<UserResponse> addUser(
        @RequestBody UserRequest userRequest
    ) {
        try {
            UserResponse user = userService.createUser(userRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<UserResponse>> getUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{user_id}")
    public ResponseEntity<UserResponse> getUserById(
        @PathVariable("user_id") long userId
    ) {
        try {
            UserResponse user = userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{user_id}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable("user_id") long userId,
        @RequestBody UserRequest userRequest
    ) {
        try {
            UserResponse user = userService.updateUser(userId, userRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (UserServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser(
        @PathVariable("user_id") long userId
    ) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (UserServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
