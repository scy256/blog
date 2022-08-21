package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.web.dto.CategoryResponseDto;
import io.github.scy256.blog.web.dto.CategorySaveRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllByUserId(Long userId){
        return categoryRepository.findAllByUserId(userId).stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(CategorySaveRequestDto categorySaveRequestDto) {
        Category category = categorySaveRequestDto.toEntity();
        categoryRepository.save(category);
    }

}
