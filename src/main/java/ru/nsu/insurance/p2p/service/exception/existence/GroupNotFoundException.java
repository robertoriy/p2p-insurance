package ru.nsu.insurance.p2p.service.exception.existence;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException() {
        super("Group not found");
    }
}
