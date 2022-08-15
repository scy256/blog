package io.github.scy256.blog.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/test")
    public String test() {
        return "user/blog";
    }

}
