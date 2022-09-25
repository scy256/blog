package io.github.scy256.blog.web.dto.post;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.comment.Comment;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.util.AuthenticationUtils;

import io.github.scy256.blog.util.PreviewUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long views;
    private User user;
    private List<Comment> comments;
    private Category category;
    private LocalDateTime createdDate;

    @Builder
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.user = post.getUser();
        this.comments = post.getComments();
        this.createdDate = post.getCreatedDate();
        this.category = post.getCategory();
    }

    public Boolean isOwner() {
        boolean isOwner = user.getId() == AuthenticationUtils.getUserFromAuthentication().getId();
        return isOwner;
    }

    public String getPreview() {
        return PreviewUtils.getPreview(content);
    }

}
