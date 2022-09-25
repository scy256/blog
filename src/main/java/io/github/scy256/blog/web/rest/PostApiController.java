package io.github.scy256.blog.web.rest;

import io.github.scy256.blog.config.auth.account.LoginAccount;
import io.github.scy256.blog.config.auth.account.SessionAccount;
import io.github.scy256.blog.service.CommentService;
import io.github.scy256.blog.service.PostService;
import io.github.scy256.blog.web.dto.comment.CommentSaveRequestDto;
import io.github.scy256.blog.web.dto.post.PostSaveRequestDto;
import io.github.scy256.blog.web.dto.post.PostUpdateRequestDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        postService.save(postSaveRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors() || !postService.isOwner(id))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        postService.update(id, postUpdateRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(!postService.isOwner(id))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        postService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<?> saveComment(@PathVariable Long id,@RequestBody @Valid CommentSaveRequestDto commentSaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        commentService.save(id, commentSaveRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        if(!commentService.isOwner(id))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        commentService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}