package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.DeviceFactory;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDeviceServiceTest {

    @Mock
    private DeviceFactory deviceFactory;

    @Mock
    private SaveDevicePort saveDevicePort;

    @Mock
    private FindUserPort findUserPort;

    @InjectMocks
    private CreateDeviceService createDeviceService;

    private User mockUser;
    private Device mockDevice;
    private String userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID().toString();

        mockUser = new User();
        mockUser.setUserId(UUID.fromString(userId));
        mockUser.setEmail("test@example.com");

        mockDevice = new Device();
        mockDevice.setDeviceId(UUID.randomUUID());
        mockDevice.setName("Test Device");
        mockDevice.setPublicKey("testPublicKey123");
        mockDevice.setIpAddress("100.64.100.1");
        mockDevice.setOwner(mockUser);
    }

    @Test
    void createDevice_ShouldReturnDevice_WhenValidDataProvided() throws IOException, InterruptedException {
        // Arrange
        String name = "Test Device";
        String pubkey = "testPublicKey123";

        when(findUserPort.findById(UUID.fromString(userId))).thenReturn(mockUser);
        when(deviceFactory.create(eq(mockUser), eq(name), any(), eq(pubkey))).thenReturn(mockDevice);
        when(saveDevicePort.save(mockDevice)).thenReturn(mockDevice);

        // Act
        Device result = createDeviceService.createDevice(userId, name, pubkey);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(pubkey, result.getPublicKey());
        verify(saveDevicePort, times(1)).save(any(Device.class));
    }

    @Test
    void createDevice_ShouldFindUser_BeforeCreatingDevice() throws IOException, InterruptedException {
        // Arrange
        String name = "Laptop";
        String pubkey = "pubkey456";

        when(findUserPort.findById(UUID.fromString(userId))).thenReturn(mockUser);
        when(deviceFactory.create(any(), any(), any(), any())).thenReturn(mockDevice);
        when(saveDevicePort.save(any())).thenReturn(mockDevice);

        // Act
        createDeviceService.createDevice(userId, name, pubkey);

        // Assert
        verify(findUserPort, times(1)).findById(UUID.fromString(userId));
    }

    @Test
    void createDevice_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        String name = "Device";
        String pubkey = "pubkey";
        when(findUserPort.findById(any())).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(Exception.class, () ->
                createDeviceService.createDevice(userId, name, pubkey)
        );
        verify(saveDevicePort, never()).save(any());
    }

    @Test
    void createDevice_ShouldAssignOwner_ToCreatedDevice() throws IOException, InterruptedException {
        // Arrange
        when(findUserPort.findById(UUID.fromString(userId))).thenReturn(mockUser);
        when(deviceFactory.create(eq(mockUser), any(), any(), any())).thenReturn(mockDevice);
        when(saveDevicePort.save(any())).thenReturn(mockDevice);

        // Act
        Device result = createDeviceService.createDevice(userId, "Device", "pubkey");

        // Assert
        assertNotNull(result.getOwner());
        assertEquals(mockUser.getUserId(), result.getOwner().getUserId());
    }
}
