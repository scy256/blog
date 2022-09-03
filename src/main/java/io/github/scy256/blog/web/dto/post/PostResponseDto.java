package io.github.scy256.blog.web.dto.post;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;

import io.github.scy256.blog.util.AuthenticationUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String preview;
    private Long views;
    private User user;
    private Category category;
    private Boolean isOwner;
    private LocalDateTime createdDate;

    @Builder
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.preview = content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        this.views = post.getViews();
        this.user = post.getUser();
        this.isOwner = user.getId() == AuthenticationUtils.getUserFromAuthentication().getId();
        this.createdDate = post.getCreatedDate();
        this.category = post.getCategory();

        if(100 < preview.length())
            preview = preview.substring(0, 100);
    }

}
