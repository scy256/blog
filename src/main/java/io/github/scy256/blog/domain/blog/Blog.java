package io.github.scy256.blog.domain.blog;


import io.github.scy256.blog.domain.BaseEntity;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Blog extends BaseEntity {

    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "userId", nullable = false)
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "blog",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "blog",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> categories;

    @Builder
    public Blog(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public Blog update(String description) {
        this.description = description;
        return this;
    }

}
