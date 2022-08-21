package io.github.scy256.blog.web.view;

import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("posts")
@Controller
public class PostController {

    private final CategoryService categoryService;

    @GetMapping("writing")
    public String getWriting(@LoginUser SessionUser sessionUser, Model model) {
        model.addAttribute("categories",categoryService.findAllByUserId(sessionUser.getId()));
        return "post/writing";
    }

}
