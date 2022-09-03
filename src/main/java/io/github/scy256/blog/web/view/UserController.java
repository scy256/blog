package io.github.scy256.blog.web.view;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user")
@Controller
public class UserController {

    @GetMapping("login")
    public String getLogin(Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated())
            return "redirect:/";
        return "user/login";
    }

}
