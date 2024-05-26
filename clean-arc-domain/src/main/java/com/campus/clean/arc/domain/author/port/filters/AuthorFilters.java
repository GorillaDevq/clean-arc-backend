package com.campus.clean.arc.domain.author.port.filters;

import com.rcore.domain.commons.port.dto.SearchFilters;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public class AuthorFilters extends SearchFilters {
    private String credentialId;
    private String email;
}
