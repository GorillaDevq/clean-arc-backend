package com.campus.clean.arc.api.author.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
