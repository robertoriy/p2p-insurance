package ru.nsu.insurance.p2p.service.exception.existence;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
