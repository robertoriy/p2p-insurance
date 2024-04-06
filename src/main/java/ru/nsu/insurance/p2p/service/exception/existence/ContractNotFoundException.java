package ru.nsu.insurance.p2p.service.exception.existence;

public class ContractNotFoundException extends NotFoundException {
    public ContractNotFoundException() {
        super("Contract not found");
    }
}
