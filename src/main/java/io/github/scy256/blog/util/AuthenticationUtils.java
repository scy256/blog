package io.github.scy256.blog.util;

import io.github.scy256.blog.config.auth.CustomOAuth2User;
import io.github.scy256.blog.domain.user.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static final User getUserFromAuthentication() {
        CustomOAuth2User principal = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

}
