package ru.nsu.insurance.p2p.service.exception.existence;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
