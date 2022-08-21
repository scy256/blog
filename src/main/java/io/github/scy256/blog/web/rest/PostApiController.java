package io.github.scy256.blog.web.rest;

import io.github.scy256.blog.config.auth.CustomOAuth2User;
import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.HttpResponseDto;
import io.github.scy256.blog.web.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto, BindingResult bindingResult, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        postService.save(postSaveRequestDto, customOAuth2User.getUser());

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
