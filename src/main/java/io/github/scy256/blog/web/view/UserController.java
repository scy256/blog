package io.github.scy256.blog.web.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    @GetMapping("/user/login")
    public String getLogin(Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated())
            return "redirect:/";
        return "user/login";
    }

}
