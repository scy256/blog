package io.github.scy256.blog.web.dto.post;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PostsResponseDto {

    private Page<PostResponseDto> posts;
    private int next;
    private int previous;

    public PostsResponseDto(Page<PostResponseDto> posts) {
        this.posts = posts;
        this.next = posts.getNumber() + 1;
        this.previous = posts.getNumber() - 1;
    }

}
