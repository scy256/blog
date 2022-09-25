package io.github.scy256.blog.config.auth.account;

import io.github.scy256.blog.domain.user.User;

import lombok.Getter;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Getter
public class SessionAccount implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private Long blogId;

    public SessionAccount(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.blogId = user.getBlog().getId();
    }

}