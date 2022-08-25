package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.web.dto.category.CategoryResponseDto;
import io.github.scy256.blog.web.dto.category.CategorySaveRequestDto;

import io.github.scy256.blog.web.dto.category.CategoryUpdateRequestDto;
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
    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
        return new CategoryResponseDto(category);
    }

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

    @Transactional
    public void update(Long id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
        String name = categoryUpdateRequestDto.getName();
        Topic topic = Topic.findByTitle(categoryUpdateRequestDto.getTopic());

        category.update(name, topic);
    }

    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Long categoryId, Long userId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
        return category.getUser().getId() == userId;
    }

}
