package io.github.scy256.blog.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("categories")
@RequiredArgsConstructor
@Controller
public class CategoryController {

    @GetMapping("setting")
    public String getSetting() {
        return "category/setting";
    }

    @GetMapping("creating")
    public String getCreating() {
        return "category/creating";
    }

}
