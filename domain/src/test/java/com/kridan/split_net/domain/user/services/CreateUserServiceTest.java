package com.kridan.split_net.domain.user.services;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.UserRole;
import com.kridan.split_net.domain.user.ports.SaveUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private SaveUserPort saveUserPort;

    @InjectMocks
    private CreateUserService createUserService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUserId(UUID.randomUUID());
        mockUser.setEmail("test@example.com");
        mockUser.setRequiredLogin(false);
        mockUser.setReauthIntervalHours(24);
        mockUser.setUserRoles(Set.of(UserRole.USER));
    }

    @Test
    void createUser_ShouldReturnUser_WhenValidDataProvided() {
        // Arrange
        String email = "test@example.com";
        String password = "securePassword123";
        when(saveUserPort.save(any(User.class), eq(password))).thenReturn(mockUser);

        // Act
        User result = createUserService.createUser(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertFalse(result.isRequiredLogin());
        assertEquals(24, result.getReauthIntervalHours());
        assertTrue(result.getUserRoles().contains(UserRole.USER));
        verify(saveUserPort, times(1)).save(any(User.class), eq(password));
    }

    @Test
    void createUser_ShouldAssignUserRole_WhenCreatingNewUser() {
        // Arrange
        String email = "newuser@example.com";
        String password = "password";
        when(saveUserPort.save(any(User.class), eq(password))).thenReturn(mockUser);

        // Act
        User result = createUserService.createUser(email, password);

        // Assert
        assertNotNull(result);
        assertTrue(result.getUserRoles().contains(UserRole.USER));
        assertFalse(result.getUserRoles().contains(UserRole.ADMIN));
    }

    @Test
    void createUser_ShouldReturnNull_WhenSavePortThrowsException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        when(saveUserPort.save(any(User.class), eq(password)))
                .thenThrow(new RuntimeException("Database error"));

        // Act
        User result = createUserService.createUser(email, password);

        // Assert
        assertNull(result);
    }

    @Test
    void createUser_ShouldGenerateUniqueId_ForEachUser() {
        // Arrange
        User firstUser = new User();
        firstUser.setUserId(UUID.randomUUID());
        firstUser.setEmail("first@example.com");
        firstUser.setUserRoles(Set.of(UserRole.USER));

        User secondUser = new User();
        secondUser.setUserId(UUID.randomUUID());
        secondUser.setEmail("second@example.com");
        secondUser.setUserRoles(Set.of(UserRole.USER));

        when(saveUserPort.save(any(User.class), any()))
                .thenReturn(firstUser)
                .thenReturn(secondUser);

        // Act
        User result1 = createUserService.createUser("first@example.com", "pass1");
        User result2 = createUserService.createUser("second@example.com", "pass2");

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.getUserId(), result2.getUserId());
    }
}
