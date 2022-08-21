package io.github.scy256.blog.config.auth;

import io.github.scy256.blog.domain.user.User;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String profileImage;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }

}
