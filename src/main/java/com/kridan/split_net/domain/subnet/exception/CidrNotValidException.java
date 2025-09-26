package com.kridan.split_net.domain.subnet.exception;

public class CidrNotValidException extends RuntimeException {
    public CidrNotValidException(String message) {
        super(message);
    }
}
