package com.campus.clean.arc.domain.author.entity;

import com.rcore.domain.commons.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class AuthorEntity extends BaseEntity<String> {
    private String credentialId;
    private String firstName;
    private String lastName;
    private String email;
}
