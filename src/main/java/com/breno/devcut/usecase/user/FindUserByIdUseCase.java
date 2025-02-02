package com.breno.devcut.usecase.user;

import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;

import java.util.UUID;

public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseUserDTO execute(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return new ResponseUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getPhone()
        );

    }

}
