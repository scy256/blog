package io.github.scy256.blog.web.view;

import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.web.dto.category.CategoryResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequestMapping("categories")
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("setting")
    public String getSetting(@LoginUser SessionUser sessionUser, Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserId(sessionUser.getId());
        model.addAttribute("categories", categories);
        return "category/setting";
    }

    @GetMapping("creating")
    public String getCreating() {
        return "category/creating";
    }

}
