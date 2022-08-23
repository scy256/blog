package io.github.scy256.blog.web.rest;

import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.post.PostSaveRequestDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        postService.save(postSaveRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
