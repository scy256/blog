package io.github.scy256.blog.web.rest;

import io.github.scy256.blog.service.CategoryService;
import io.github.scy256.blog.web.dto.CategorySaveRequestDto;

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
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CategorySaveRequestDto categorySaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        categoryService.save(categorySaveRequestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
