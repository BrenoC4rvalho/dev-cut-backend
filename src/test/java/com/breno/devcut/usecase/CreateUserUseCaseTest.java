package com.breno.devcut.usecase;

import com.breno.devcut.enums.Role;
import com.breno.devcut.exceptions.UserAlreadyExistsException;
import com.breno.devcut.model.dto.user.CreateUserDTO;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;
import com.breno.devcut.security.PasswordEncryptor;
import com.breno.devcut.usecase.user.CreateUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void should_create_user_successfully() {

        CreateUserDTO dto = new CreateUserDTO("user", "password", Role.CLIENT, "24999256787");

        UUID id = UUID.randomUUID();

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setUsername(dto.getUsername());
        expectedUser.setPassword("encryptedPassword");
        expectedUser.setRole(dto.getRole());
        expectedUser.setPhone(dto.getPhone());

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(passwordEncryptor.encrypt(dto.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        ResponseUserDTO result = createUserUseCase.execute(dto);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals(Role.CLIENT, result.getRole());
        assertEquals("24999256787", dto.getPhone());

    }

    @Test
    void should_throw_userAlreadyExistsException_when_username_is_taken() {
        CreateUserDTO dto = new CreateUserDTO("user", "password", Role.CLIENT, "24999256787");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> createUserUseCase.execute(dto));
    }

}
