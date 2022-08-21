package io.github.scy256.blog.web.view;

import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.web.dto.CategoryResponseDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("posts")
@Controller
public class PostController {

    private final CategoryService categoryService;

    @GetMapping("writing")
    public String getWriting(@LoginUser SessionUser sessionUser, Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserId(sessionUser.getId());
        model.addAttribute("categories", categories);
        return "post/writing";
    }

}
