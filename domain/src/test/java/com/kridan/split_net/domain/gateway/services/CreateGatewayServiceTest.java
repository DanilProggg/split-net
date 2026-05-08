package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGatewayServiceTest {

    @Mock
    private SaveGatewayPort saveGatewayPort;

    @Mock
    private FindSitePort findSitePort;

    @InjectMocks
    private CreateGatewayService createGatewayService;

    private Site mockSite;
    private Gateway mockGateway;
    private String gatewayId;
    private String siteId;

    @BeforeEach
    void setUp() {
        gatewayId = UUID.randomUUID().toString();
        siteId = UUID.randomUUID().toString();

        mockSite = new Site();
        mockSite.setSiteId(UUID.fromString(siteId));
        mockSite.setName("Test Site");

        mockGateway = Gateway.builder()
                .gatewayId(UUID.fromString(gatewayId))
                .name("Test Gateway")
                .ipAddress("100.64.0.1")
                .site(mockSite)
                .build();
    }

    @Test
    void create_ShouldReturnGateway_WhenValidDataProvided() {
        // Arrange
        String name = "Test Gateway";
        when(findSitePort.findById(siteId)).thenReturn(mockSite);
        when(saveGatewayPort.save(any(Gateway.class))).thenReturn(mockGateway);

        // Act
        Gateway result = createGatewayService.create(gatewayId, name, siteId);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(UUID.fromString(gatewayId), result.getGatewayId());
        verify(saveGatewayPort, times(1)).save(any(Gateway.class));
    }

    @Test
    void create_ShouldFindSite_BeforeCreatingGateway() {
        // Arrange
        when(findSitePort.findById(siteId)).thenReturn(mockSite);
        when(saveGatewayPort.save(any())).thenReturn(mockGateway);

        // Act
        createGatewayService.create(gatewayId, "Gateway", siteId);

        // Assert
        verify(findSitePort, times(1)).findById(siteId);
    }

    @Test
    void create_ShouldAssignSite_ToCreatedGateway() {
        // Arrange
        when(findSitePort.findById(siteId)).thenReturn(mockSite);
        when(saveGatewayPort.save(any())).thenReturn(mockGateway);

        // Act
        Gateway result = createGatewayService.create(gatewayId, "Gateway", siteId);

        // Assert
        assertNotNull(result.getSite());
        assertEquals(mockSite.getSiteId(), result.getSite().getSiteId());
    }

    @Test
    void create_ShouldThrowException_WhenSiteNotFound() {
        // Arrange
        when(findSitePort.findById(siteId)).thenThrow(new RuntimeException("Site not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                createGatewayService.create(gatewayId, "Gateway", siteId)
        );
        verify(saveGatewayPort, never()).save(any());
    }
}
