package io.github.scy256.blog.web.view;


import io.github.scy256.blog.config.auth.account.LoginAccount;
import io.github.scy256.blog.config.auth.account.SessionAccount;
import io.github.scy256.blog.service.BlogService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.blog.BlogResponseDto;
import io.github.scy256.blog.web.dto.post.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final PostService postService;

    private final BlogService blogService;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/blog")
    public String getSessionBlog(@LoginAccount SessionAccount sessionAccount) {
        return "redirect:/blog/" + sessionAccount.getBlogId();
    }

    @GetMapping("/blog/{id}")
    public String getBlog(@PathVariable Long id, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        BlogResponseDto blog = blogService.findById(id);
        PostsResponseDto posts = postService.findAllByBlogId(id, pageable);

        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("search", "전체");

        return "user/blog";
    }

    @GetMapping("/blog/{id}/search/category")
    public String searchCategory(@PathVariable Long id, @Param("category") String category, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        BlogResponseDto blog = blogService.findById(id);
        PostsResponseDto posts = postService.findAllByCategoryName(id, category, pageable);

        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("search", category);

        return "user/blog";
    }

    @GetMapping("/blog/{id}/search")
    public String searchTitle(@PathVariable Long id, @Param("keyword") String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        BlogResponseDto blog = blogService.findById(id);
        PostsResponseDto posts = postService.search(id, keyword, pageable);

        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("search", keyword);

        return "user/blog";
    }

}
