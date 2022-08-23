package io.github.scy256.blog.web.view;

import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.service.UserService;
import io.github.scy256.blog.web.dto.post.PostResponseDto;

import org.springframework.data.domain.Page;
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

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/blog/{userId}")
    public String getBlog(@PathVariable Long userId, Model model, @LoginUser SessionUser sessionUser, @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> posts = postService.findAllByUserId(userId, pageable);

        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("posts", posts);
        model.addAttribute("nextNumber", posts.getNumber() + 1);
        model.addAttribute("previousNumber", posts.getNumber() - 1);
        model.addAttribute("isOwner", userId.equals(sessionUser.getId()));

        return "user/blog";
    }

}
