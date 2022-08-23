package io.github.scy256.blog.web.rest;

import io.github.scy256.blog.config.auth.LoginUser;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.web.dto.category.CategorySaveRequestDto;

import io.github.scy256.blog.web.dto.category.CategoryUpdateRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody  CategorySaveRequestDto categorySaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>("잘못된 요청입니다", HttpStatus.BAD_REQUEST);
        categoryService.save(categorySaveRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @LoginUser SessionUser sessionUser, @Valid @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors() || categoryService.isOwner(id, sessionUser.getId()))
            return new ResponseEntity<>("잘못된 요청입니다", HttpStatus.BAD_REQUEST);
        categoryService.update(id, categoryUpdateRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
