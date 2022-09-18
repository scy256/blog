package io.github.scy256.blog.web.view;

import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.service.UserService;
import io.github.scy256.blog.util.AuthenticationUtils;

import io.github.scy256.blog.web.dto.category.CategoryResponseDto;
import io.github.scy256.blog.web.dto.post.PostResponseDto;
import io.github.scy256.blog.web.dto.post.PostsResponseDto;
import io.github.scy256.blog.web.dto.user.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final PostService postService;

    private final UserService userService;

    private final CategoryService categoryService;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/blog")
    public String getUserBlog() {
        Long id = AuthenticationUtils.getUserFromAuthentication().getId();
        return "redirect:/blog/" + id;
    }

    @GetMapping("/blog/{userId}")
    public String getBlog(@PathVariable Long userId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        UserResponseDto user = userService.findById(userId);
        List<CategoryResponseDto> categories = categoryService.findAllByUserId(userId);
        PostsResponseDto dto = postService.findAllByUserId(userId, pageable);

        model.addAttribute("user", user);
        model.addAttribute("categories", categories);
        model.addAttribute("dto", dto);

        return "user/blog";
    }

}
