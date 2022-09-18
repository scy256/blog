package io.github.scy256.blog.domain.post;

import io.github.scy256.blog.domain.BaseEntity;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.comment.Comment;
import io.github.scy256.blog.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long views;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "categoryId", nullable = false)
    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, User user, Category category, Long views) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.views = views;
        this.topic = category.getTopic();
    }

    public Post update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.topic = category.getTopic();
        return this;
    }

    public Post increaseViews() {
        views++;
        return this;
    }

}
