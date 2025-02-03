package com.breno.devcut.model.dto.user;

import com.breno.devcut.enums.Role;

import java.util.UUID;

public class ResponseUserDTO {

    private UUID id;
    private String username;
    private Role role;
    private String phone;

    public ResponseUserDTO(UUID id, String username, Role role, String phone) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.phone = phone;
    }

    private ResponseUserDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
