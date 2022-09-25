package io.github.scy256.blog.web.view;


import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.category.CategoryResponseDto;
import io.github.scy256.blog.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    private final CategoryService categoryService;

    @GetMapping("/posts/writing")
    public String getWriting(Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserFromAuthentication();
        model.addAttribute("categories", categories);
        return "post/writing";
    }

    @GetMapping("/posts/edit/{id}")
    public String getEdit(@PathVariable Long id, Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserFromAuthentication();
        PostResponseDto post = postService.findById(id);

        model.addAttribute("categories", categories);
        model.addAttribute("post", post);

        return "post/edit";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        postService.increaseViews(id);
        PostResponseDto post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/detail";
    }

}
