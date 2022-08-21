package io.github.scy256.blog.domain.category;

import io.github.scy256.blog.domain.BaseTimeEntity;
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
public class Category extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    public Category update(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
        return this;
    }

    @Builder
    public Category(String name, Topic topic, User user){
        this.name = name;
        this.topic = topic;
        this.user = user;
    }

}
