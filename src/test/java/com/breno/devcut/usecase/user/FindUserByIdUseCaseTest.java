package com.breno.devcut.usecase.user;

import com.breno.devcut.enums.Role;
import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @Test
    void should_return_exists_user() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "testUser", "12345678", Role.ADMIN, "24994567890");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseUserDTO result = findUserByIdUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testUser", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals("24994567890", result.getPhone());

    }

    @Test
    void should_return_exception_user_not_found() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> findUserByIdUseCase.execute(userId));
    }

}