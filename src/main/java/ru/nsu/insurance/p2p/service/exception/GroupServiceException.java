package ru.nsu.insurance.p2p.service.exception;

public class GroupServiceException extends RuntimeException {
    public GroupServiceException(String message) {
        super(message);
    }

    public GroupServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
