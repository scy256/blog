package io.github.scy256.blog.web.view;

import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.service.CommentService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.category.CategoryResponseDto;
import io.github.scy256.blog.web.dto.comment.CommentResponseDto;
import io.github.scy256.blog.web.dto.post.PostResponseDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("posts")
@Controller
public class PostController {

    private final PostService postService;

    private final CategoryService categoryService;

    private final CommentService commentService;

    @GetMapping("writing")
    public String getWriting(Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserFromAuthentication();
        model.addAttribute("categories", categories);
        return "post/writing";
    }

    @GetMapping("editing/{id}")
    public String getEditing(@PathVariable  Long id,Model model) {
        List<CategoryResponseDto> categories = categoryService.findAllByUserFromAuthentication();
        PostResponseDto postResponseDto = postService.findById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("dto", postResponseDto);
        return "post/editing";
    }

    @GetMapping("{id}")
    public String getPost(@PathVariable Long id, Model model) {
        postService.increaseViews(id);
        PostResponseDto dto = postService.findById(id);
        List<CommentResponseDto> comments = commentService.findAllByPostId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("dto", dto);
        return "post/detail";
    }

}
