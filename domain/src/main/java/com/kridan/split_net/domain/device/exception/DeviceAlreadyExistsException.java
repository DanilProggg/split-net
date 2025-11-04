package com.kridan.split_net.domain.device.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
    public DeviceAlreadyExistsException(String message) {
        super(message);
    }
}
