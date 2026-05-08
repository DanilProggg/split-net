package com.kridan.split_net.domain.gateway.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RefreshGatewayInfoServiceTest {

    @Mock
    private FindGatewayPort findGatewayPort;

    @Mock
    private SaveGatewayPort saveGatewayPort;

    @InjectMocks
    private RefreshGatewayInfoService refreshGatewayInfoService;

    @Test
    void refresh_ShouldUpdateGatewayAndSave() {
        // Arrange
        UUID gatewayId = UUID.randomUUID();
        String hostname = "gateway-1";
        String publicKey = "pub-key";
        String wgUrl = "http://wg.local";

        Gateway gateway = new Gateway();
        gateway.setGatewayId(gatewayId);
        gateway.setName("old-name");
        gateway.setPublicKey("old-key");
        gateway.setWgUrl("old-url");

        when(findGatewayPort.findById(gatewayId.toString())).thenReturn(gateway);

        // Act
        refreshGatewayInfoService.refresh(gatewayId.toString(), hostname, publicKey, wgUrl);

        // Assert
        assertEquals(hostname, gateway.getName());
        assertEquals(publicKey, gateway.getPublicKey());
        assertEquals(wgUrl, gateway.getWgUrl());

        verify(findGatewayPort, times(1)).findById(gatewayId.toString());
        verify(saveGatewayPort, times(1)).save(gateway);
    }

    @Test
    void refresh_ShouldThrowException_WhenGatewayNotFound() {
        // Arrange
        String gatewayId = "missing";

        when(findGatewayPort.findById(gatewayId))
                .thenThrow(new RuntimeException("Gateway not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                refreshGatewayInfoService.refresh(
                        gatewayId,
                        "host",
                        "key",
                        "url"
                )
        );

        verify(saveGatewayPort, never()).save(any());
    }

    @Test
    void refresh_ShouldNotSave_WhenFindFailsBeforeMutation() {
        // Arrange
        String gatewayId = "gw-2";

        when(findGatewayPort.findById(gatewayId))
                .thenThrow(new RuntimeException("DB error"));

        // Act
        assertThrows(RuntimeException.class, () ->
                refreshGatewayInfoService.refresh(
                        gatewayId,
                        "host",
                        "key",
                        "url"
                )
        );

        // Assert
        verify(saveGatewayPort, never()).save(any());
    }
}