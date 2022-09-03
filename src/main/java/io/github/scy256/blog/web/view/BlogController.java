package io.github.scy256.blog.web.view;

import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.service.UserService;
import io.github.scy256.blog.util.AuthenticationUtils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

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

    @GetMapping("/blog/{userId}")
    public String getBlog(@PathVariable Long userId, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("categories", categoryService.findAllByUserId(userId));
        model.addAttribute("dto", postService.findAllByUserId(userId, pageable));
        return "user/blog";
    }

    @GetMapping("/blog")
    public String getUserBlog() {
        return "redirect:/blog/" + AuthenticationUtils.getUserFromAuthentication().getId();
    }

}
