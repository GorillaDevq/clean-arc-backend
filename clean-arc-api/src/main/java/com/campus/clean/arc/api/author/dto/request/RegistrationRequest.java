package com.campus.clean.arc.api.author.dto.request;

import lombok.Getter;

@Getter
public class RegistrationRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
