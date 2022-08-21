package io.github.scy256.blog.domain.post;

import io.github.scy256.blog.domain.BaseTimeEntity;
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
public class Post extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "categoryId", nullable = false)
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    public Post update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
        return this;
    }

    @Builder
    public Post(String title, String content, User user, Category category) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.topic = category.getTopic();
    }

}
