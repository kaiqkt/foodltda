package com.zed.auth.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The field 'username' is mandatory")
    @Column(nullable = false)
    private String username;
    @NotEmpty(message = "The field 'password' is mandatory")
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @NotEmpty(message = "The field 'password' is mandatory")
    @Column(nullable = false)
    private String role = "USER";

    public User() {
    }

    public User(@NotNull User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
