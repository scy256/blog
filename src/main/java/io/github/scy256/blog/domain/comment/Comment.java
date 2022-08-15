package io.github.scy256.blog.domain.comment;

import io.github.scy256.blog.domain.BaseTimeEntity;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "postId", nullable = false)
    @ManyToOne
    private Post post;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

}
