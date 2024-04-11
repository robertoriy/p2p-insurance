package ru.nsu.insurance.p2p.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User Controller", description = "Контроллер для управления пользователями")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Создать пользователя")
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

    @Operation(summary = "Получить список всех пользователей")
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

    @Operation(summary = "Получить информацию о конкретном пользователе")
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

    @Operation(summary = "Обновить информацию о существующем пользователе")
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

    @Operation(summary = "Удалить пользователя")
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
