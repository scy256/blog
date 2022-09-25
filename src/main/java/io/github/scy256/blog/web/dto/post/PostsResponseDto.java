package io.github.scy256.blog.web.dto.post;

import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.web.dto.user.UserResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PostsResponseDto {

    private Page<PostResponseDto> page;
    private int next;
    private int previous;

    public PostsResponseDto(Page<Post> posts) {
        this.page = posts.map(post -> new PostResponseDto(post));
        this.next = posts.getNumber() + 1;
        this.previous = posts.getNumber() - 1;
    }

}
