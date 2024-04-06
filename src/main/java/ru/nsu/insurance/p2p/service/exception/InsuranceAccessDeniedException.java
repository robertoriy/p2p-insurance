package ru.nsu.insurance.p2p.service.exception;

public class InsuranceAccessDeniedException extends RuntimeException {
    public InsuranceAccessDeniedException() {
        super("Access denied");
    }
}
