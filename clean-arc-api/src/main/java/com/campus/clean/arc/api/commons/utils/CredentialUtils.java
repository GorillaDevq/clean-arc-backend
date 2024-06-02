package com.campus.clean.arc.api.commons.utils;

import com.rcore.rest.api.spring.security.TokenAuthentication;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class CredentialUtils {
    public String getCredentialId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof TokenAuthentication)) return null;

        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        return tokenAuthentication.getPrincipal().getId();
    }
}
