package io.github.scy256.blog.domain.category;

import io.github.scy256.blog.domain.BaseEntity;
import io.github.scy256.blog.domain.blog.Blog;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    @JoinColumn(name = "blogId", nullable = false)
    @ManyToOne
    private Blog blog;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts;

    @Builder
    public Category(String name, Topic topic, User user){
        this.name = name;
        this.topic = topic;
        this.user = user;
        this.blog = user.getBlog();
    }

    public Category update(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
        return this;
    }

}
