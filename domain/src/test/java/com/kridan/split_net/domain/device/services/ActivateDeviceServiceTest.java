package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.application.outbound.rabbitmq.EventPublisherService;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.ports.FindDevicePort;
import com.kridan.split_net.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateDeviceServiceTest {

    @Mock
    private FindDevicePort findDevicePort;

    @Mock
    private EventPublisherService eventPublisherService;

    @InjectMocks
    private ActivateDeviceService activateDeviceService;

    private Device mockDevice;
    private User mockUser;
    private String deviceId;

    @BeforeEach
    void setUp() {
        deviceId = UUID.randomUUID().toString();

        mockUser = new User();
        mockUser.setUserId(UUID.randomUUID());
        mockUser.setReauthIntervalHours(24);

        mockDevice = new Device();
        mockDevice.setDeviceId(UUID.randomUUID());
        mockDevice.setPublicKey("testPublicKey");
        mockDevice.setIpAddress("100.64.100.1");
        mockDevice.setOwner(mockUser);
    }

    @Test
    void activate_ShouldPublishEvent_WhenDeviceHasNeverBeenActivated() {
        // Arrange
        mockDevice.setLastActivation(null);
        when(findDevicePort.findById(deviceId)).thenReturn(mockDevice);

        // Act
        activateDeviceService.activate(deviceId);

        // Assert
        verify(eventPublisherService, times(1)).publishEvent(any(), eq("add"));
    }

    @Test
    void activate_ShouldPublishEvent_WhenActivationPeriodExpired() {
        // Arrange
        Date expiredDate = Date.from(Instant.now().minus(25, ChronoUnit.HOURS));
        mockDevice.setLastActivation(expiredDate);
        when(findDevicePort.findById(deviceId)).thenReturn(mockDevice);

        // Act
        activateDeviceService.activate(deviceId);

        // Assert
        verify(eventPublisherService, times(1)).publishEvent(any(), eq("add"));
    }

    @Test
    void activate_ShouldNotPublishEvent_WhenActivationPeriodNotExpired() {
        // Arrange
        Date recentDate = Date.from(Instant.now().minus(1, ChronoUnit.HOURS));
        mockDevice.setLastActivation(recentDate);
        when(findDevicePort.findById(deviceId)).thenReturn(mockDevice);

        // Act
        activateDeviceService.activate(deviceId);

        // Assert
        verify(eventPublisherService, never()).publishEvent(any(), any());
    }

    @Test
    void isPeriodExpired_ShouldReturnTrue_WhenPeriodExceeded() {
        // Arrange
        Instant pastTime = Instant.now().minus(25, ChronoUnit.HOURS);

        // Act
        boolean result = activateDeviceService.isPeriodExpired(pastTime, 24);

        // Assert
        assertTrue(result);
    }

    @Test
    void isPeriodExpired_ShouldReturnFalse_WhenPeriodNotExceeded() {
        // Arrange
        Instant recentTime = Instant.now().minus(1, ChronoUnit.HOURS);

        // Act
        boolean result = activateDeviceService.isPeriodExpired(recentTime, 24);

        // Assert
        assertFalse(result);
    }

    @Test
    void isPeriodExpired_ShouldReturnFalse_WhenExactlyAtBoundary() {
        // Arrange
        Instant boundaryTime = Instant.now().minus(24, ChronoUnit.HOURS).plusSeconds(10);

        // Act
        boolean result = activateDeviceService.isPeriodExpired(boundaryTime, 24);

        // Assert
        assertFalse(result);
    }
}
