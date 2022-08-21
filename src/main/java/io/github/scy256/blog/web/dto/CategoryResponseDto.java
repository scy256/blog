package io.github.scy256.blog.web.dto;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.post.Post;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryResponseDto {

    private Long id;

    private String name;

    private Topic topic;

    private List<Post> posts;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.topic = category.getTopic();
        this.posts = category.getPosts();
    }

}
