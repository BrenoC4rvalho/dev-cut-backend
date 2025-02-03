package com.breno.devcut.model.entities;

import com.breno.devcut.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$", message = "The username field must not contain blank spaces.")
    @Length(min = 3, max = 20, message = "The username field between 3 and 20 characters.")
    @Column(unique = true)
    private String username;

    @Length(min = 6, message = "The password field cannot be shorter than 6.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @NotBlank(message = "The phone field cannot be empty.")
    private String phone;

    public User() {}

    public User(UUID id, String username, String password, Role role, String phone) {
        this.id = id;
        this.username = username;
        this. password = password;
        this.role = role;
        this.phone = phone;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
