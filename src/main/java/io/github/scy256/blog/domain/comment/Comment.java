package io.github.scy256.blog.domain.comment;

import io.github.scy256.blog.domain.BaseEntity;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.util.AuthenticationUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "postId", nullable = false)
    @ManyToOne
    private Post post;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;

    @Builder
    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public Boolean isOwner() {
        User sessionUser = AuthenticationUtils.getUserFromAuthentication();
        return user.getId() == sessionUser.getId();
    }

}
