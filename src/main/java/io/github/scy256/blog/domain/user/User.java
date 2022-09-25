package io.github.scy256.blog.domain.user;

import io.github.scy256.blog.domain.BaseEntity;

import io.github.scy256.blog.domain.blog.Blog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String profileImage;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Blog blog;

    @Builder
    public User(String name, String email, String profileImage, String provider, String providerId, Role role) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public User update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
        return this;
    }

    public String getRoleKey() {
        return role.getKey();
    }

}
