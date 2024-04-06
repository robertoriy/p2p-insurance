package ru.nsu.insurance.p2p.service.exception;

public class InsuranceServiceException extends RuntimeException {
    public InsuranceServiceException(String message) {
        super(message);
    }

    public InsuranceServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
