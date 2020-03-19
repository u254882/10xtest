package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class User {
    @NotBlank(message = "Missing name")
    private String name;
    @NotBlank(message = "Missing address")
    private String address;
    @NotBlank(message = "Missing email")
    private String email;

    public User(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
