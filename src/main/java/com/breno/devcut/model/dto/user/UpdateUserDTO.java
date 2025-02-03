package com.breno.devcut.model.dto.user;

import java.util.Optional;

public class UpdateUserDTO {

    private Optional<String> password;
    private Optional<String> phone;

    public UpdateUserDTO(Optional<String> password, Optional<String> phone) {
        this.password = password;
        this.phone = phone;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public Optional<String> getPhone() {
        return phone;
    }
}
