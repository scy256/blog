package io.github.scy256.blog.web.dto.comment;

import io.github.scy256.blog.domain.comment.Comment;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.util.AuthenticationUtils;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private User user;
    private Boolean isOwner;
    private LocalDateTime createdDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = comment.getUser();
        this.isOwner = AuthenticationUtils.getUserFromAuthentication().getId() == user.getId();
        this.createdDate = comment.getCreatedDate();
    }

}
