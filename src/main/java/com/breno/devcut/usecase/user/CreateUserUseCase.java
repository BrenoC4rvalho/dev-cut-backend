package com.breno.devcut.usecase.user;

import com.breno.devcut.enums.Role;
import com.breno.devcut.exceptions.PasswordNotEncryptedException;
import com.breno.devcut.exceptions.UserAlreadyExistsException;
import com.breno.devcut.model.dto.user.CreateUserDTO;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;
import com.breno.devcut.security.PasswordEncryptor;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public ResponseUserDTO execute(CreateUserDTO dto) {

        boolean existsUser = userRepository.existsByUsername(dto.getUsername());
        if(existsUser) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = passwordEncryptor.encrypt(dto.getPassword());
        if(encryptedPassword.equals(dto.getPassword())) {
            throw new PasswordNotEncryptedException();
        }

        Role role = dto.getRole() != null ? dto.getRole() : Role.CLIENT;

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(role);
        newUser.setPhone(dto.getPhone());

        User savedUser = userRepository.save(newUser);

        return new ResponseUserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole(),
                savedUser.getPhone()
        );

    }

}
