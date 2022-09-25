package io.github.scy256.blog.web.dto.blog;

import io.github.scy256.blog.domain.blog.Blog;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.util.AuthenticationUtils;

import java.util.List;

public class BlogResponseDto {

    private Long id;
    private String description;
    private User user;
    private List<Post> posts;
    private List<Category> categories;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.description = blog.getDescription();
        this.user = blog.getUser();
        this.posts = blog.getPosts();
        this.categories = blog.getCategories();
    }

    public Boolean isOwner() {
        User account = AuthenticationUtils.getUserFromAuthentication();
        return user.getId() == account.getId();
    }

}
