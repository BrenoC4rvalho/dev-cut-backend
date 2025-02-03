package com.breno.devcut.usecase.user;

import com.breno.devcut.enums.Role;
import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.dto.user.UpdateUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;
import com.breno.devcut.security.PasswordEncryptor;
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
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Test
    void should_update_user_phone_and_password_successfully() {
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(Optional.of("newPassword"), Optional.of("987654321"));

        User user = new User(userId, "testUser", "oldPassword", Role.CLIENT, "123456789");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncryptor.encrypt(updateUserDTO.getPassword().get())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseUserDTO updatedUser = updateUserUseCase.execute(userId, updateUserDTO);

        assertNotNull(updatedUser);
        assertEquals("987654321", updatedUser.getPhone());
        assertNotEquals("oldPassword", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void should_throw_userNotFountException() {
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(Optional.of("newPassword"), Optional.of("987654321"));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.execute(userId, updateUserDTO));
    }

    @Test
    void should_throw_illegalArgumentException_when_password_is_less_than_6_characters() {
        UUID userId = UUID.randomUUID();
        UpdateUserDTO dto = new UpdateUserDTO(Optional.of("12345"), Optional.empty());

        User user = new User(userId, "testUser", "oldPassword", Role.CLIENT, "123456789");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(userId, dto));
    }

}