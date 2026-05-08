package com.kridan.split_net.domain.policy.services;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.policy.ports.SavePolicyPort;
import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.FindResourcePort;
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
class CreatePolicyServiceTest {

    @Mock
    private SavePolicyPort savePolicyPort;

    @Mock
    private FindGroupPort findGroupPort;

    @Mock
    private FindResourcePort findResourcePort;

    @InjectMocks
    private CreatePolicyService createPolicyService;

    private Group mockGroup;
    private Resource mockResource;
    private Policy mockPolicy;
    private String groupId;
    private String resourceId;

    @BeforeEach
    void setUp() {
        groupId = UUID.randomUUID().toString();
        resourceId = UUID.randomUUID().toString();

        mockGroup = new Group();
        mockGroup.setGroupId(UUID.fromString(groupId));
        mockGroup.setName("Test Group");

        mockResource = new Resource();
        mockResource.setResourceId(UUID.fromString(resourceId));
        mockResource.setDestination("192.168.1.0/24");

        mockPolicy = Policy.builder()
                .policyId(UUID.randomUUID())
                .group(mockGroup)
                .resource(mockResource)
                .description("Test policy")
                .build();
    }

    @Test
    void create_ShouldReturnPolicy_WhenValidDataProvided() {
        // Arrange
        String description = "Test policy";
        when(findGroupPort.findById(groupId)).thenReturn(mockGroup);
        when(findResourcePort.findById(resourceId)).thenReturn(mockResource);
        when(savePolicyPort.save(any(Policy.class))).thenReturn(mockPolicy);

        // Act
        Policy result = createPolicyService.create(resourceId, groupId, description);

        // Assert
        assertNotNull(result);
        assertEquals(description, result.getDescription());
        assertEquals(mockGroup, result.getGroup());
        assertEquals(mockResource, result.getResource());
        verify(savePolicyPort, times(1)).save(any(Policy.class));
    }

    @Test
    void create_ShouldFindGroupAndResource_BeforeCreatingPolicy() {
        // Arrange
        when(findGroupPort.findById(groupId)).thenReturn(mockGroup);
        when(findResourcePort.findById(resourceId)).thenReturn(mockResource);
        when(savePolicyPort.save(any())).thenReturn(mockPolicy);

        // Act
        createPolicyService.create(resourceId, groupId, "description");

        // Assert
        verify(findGroupPort, times(1)).findById(groupId);
        verify(findResourcePort, times(1)).findById(resourceId);
    }

    @Test
    void create_ShouldThrowException_WhenGroupNotFound() {
        // Arrange
        when(findGroupPort.findById(groupId)).thenThrow(new RuntimeException("Group not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                createPolicyService.create(resourceId, groupId, "description")
        );
        verify(savePolicyPort, never()).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenResourceNotFound() {
        // Arrange
        when(findGroupPort.findById(groupId)).thenReturn(mockGroup);
        when(findResourcePort.findById(resourceId)).thenThrow(new RuntimeException("Resource not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                createPolicyService.create(resourceId, groupId, "description")
        );
        verify(savePolicyPort, never()).save(any());
    }

    @Test
    void create_ShouldLinkGroupAndResource_InCreatedPolicy() {
        // Arrange
        when(findGroupPort.findById(groupId)).thenReturn(mockGroup);
        when(findResourcePort.findById(resourceId)).thenReturn(mockResource);
        when(savePolicyPort.save(any())).thenReturn(mockPolicy);

        // Act
        Policy result = createPolicyService.create(resourceId, groupId, "description");

        // Assert
        assertEquals(mockGroup.getGroupId(), result.getGroup().getGroupId());
        assertEquals(mockResource.getResourceId(), result.getResource().getResourceId());
    }
}
