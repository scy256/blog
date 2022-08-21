package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.web.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> findAllByUserId(Long userId){
        return categoryRepository.findAllByUserId(userId).stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

}
