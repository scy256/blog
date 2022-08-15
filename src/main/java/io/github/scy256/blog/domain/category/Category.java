package io.github.scy256.blog.domain.category;

import io.github.scy256.blog.domain.BaseTimeEntity;

import io.github.scy256.blog.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
