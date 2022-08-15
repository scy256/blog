package io.github.scy256.blog.config.auth.provider;

import io.github.scy256.blog.domain.user.Role;
import io.github.scy256.blog.domain.user.User;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected final Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getProvider();

    public abstract String getProviderId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getProfileImage();

    public User toEntity() {
        return User.builder()
                .provider(getProvider())
                .providerId(getProviderId())
                .name(getName())
                .email(getEmail())
                .profileImage(getProfileImage())
                .role(Role.USER)
                .build();
    }

}
