package com.breno.devcut.usecase.user;

import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.user.ResponseUserDTO;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserUseCase {

    private final UserRepository userRepository;

    public GetAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ResponseUserDTO> execute() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users.stream()
                .map(user -> new ResponseUserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getPhone()
                )).collect(Collectors.toList());

    }

}
