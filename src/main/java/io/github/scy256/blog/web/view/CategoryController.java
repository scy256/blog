package io.github.scy256.blog.web.view;

import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.web.dto.category.CategoryResponseDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("setting")
    public String getSetting(Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserFromAuthentication();
        model.addAttribute("categories", categories);
        return "category/setting";
    }

    @GetMapping("creating")
    public String getCreating() {
        return "category/creating";
    }

    @GetMapping("edit/{id}")
    public String getUpdating(@PathVariable Long id, Model model) {
        CategoryResponseDto category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category/edit";
    }

}
