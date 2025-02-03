package com.breno.devcut.usecase.user;

import com.breno.devcut.exceptions.PasswordNotEncryptedException;
import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.dto.user.UpdateUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;
import com.breno.devcut.security.PasswordEncryptor;

import java.util.UUID;

public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public UpdateUserUseCase(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public ResponseUserDTO execute(UUID userId, UpdateUserDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(dto.getPassword().isPresent()) {
            if(dto.getPassword().get().length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }

            String encryptedPassword = passwordEncryptor.encrypt(dto.getPassword().get());
            if(encryptedPassword.equals(dto.getPassword().get())) {
                throw new PasswordNotEncryptedException();
            }

            user.setPassword(encryptedPassword);
        }

        if(dto.getPhone().isPresent()) {
            user.setPhone(dto.getPhone().get());
        }

        User updatedUser = userRepository.save(user);

        return new ResponseUserDTO(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getRole(),
                updatedUser.getPhone()
        );

    }

}
