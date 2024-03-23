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
import ru.nsu.insurance.p2p.dto.request.GroupRequest;
import ru.nsu.insurance.p2p.dto.response.GroupResponse;
import ru.nsu.insurance.p2p.service.exception.GroupServiceException;
import ru.nsu.insurance.p2p.service.exception.existence.NotFoundException;
import ru.nsu.insurance.p2p.service.group.GroupService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class GroupController {
    private final GroupService groupService;

    @PostMapping(value = "/{user_id}/groups")
    public ResponseEntity<GroupResponse> addGroup(
        @PathVariable("user_id") long userId,
        @RequestBody GroupRequest groupRequest
    ) {
        try {
            GroupResponse groupResponse = groupService.createGroup(userId, groupRequest);
            return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (GroupServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{user_id}/groups")
    public ResponseEntity<List<GroupResponse>> getGroups(
        @PathVariable("user_id") long userId
    ) {
        return null;
    }

    @GetMapping(value = "/{user_id}/groups/{group_id}")
    public ResponseEntity<GroupResponse> getGroup(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId
    ) {
        try {
            GroupResponse group = groupService.getGroup(userId, groupId);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (NotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        } catch (GroupServiceException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{user_id}/groups/{group_id}")
    public ResponseEntity<GroupResponse> updateGroup(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId
    ) {
        return null;
    }

    @DeleteMapping(value = "/{user_id}/groups/{group_id}")
    public ResponseEntity<HttpStatus> deleteGroup(
        @PathVariable("user_id") long userId,
        @PathVariable("group_id") long groupId,
        @RequestBody GroupRequest groupRequest
    ) {
        return null;
    }
}
