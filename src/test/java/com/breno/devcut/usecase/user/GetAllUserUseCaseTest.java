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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUserUseCase getAllUserUseCase;

    @Test
    void should_return_all_users_successfully() {
        User user1 = new User(UUID.randomUUID(), "user1", "password", Role.CLIENT, "24567890456");
        User user2 = new User(UUID.randomUUID(), "user2", "password", Role.ADMIN, "24679890488");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<ResponseUserDTO> users = getAllUserUseCase.execute();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals(Role.CLIENT, users.get(0).getRole());
        assertEquals("user2", users.get(1).getUsername());
        assertEquals(Role.ADMIN, users.get(1).getRole());

    }

    @Test
    void should_return_exception_user_not_found() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> getAllUserUseCase.execute());
    }

}