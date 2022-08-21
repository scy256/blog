package io.github.scy256.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.scy256.blog.domain.BaseTimeEntity;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Category> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Post> posts;

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
