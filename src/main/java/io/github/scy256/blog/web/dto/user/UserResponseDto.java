package io.github.scy256.blog.web.dto.user;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;

import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String profileImage;
    private String role;
    private List<Category> categories;
    private List<Post> posts;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
        this.role = user.getRoleKey();
        this.categories = user.getCategories();
        this.posts = user.getPosts();
    }

}
