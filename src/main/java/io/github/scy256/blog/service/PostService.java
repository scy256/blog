package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.post.PostRepository;

import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.web.dto.PostResponseDto;
import io.github.scy256.blog.web.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByUserId(userId, pageable);
        return posts.map(post -> new PostResponseDto(post));
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(PostSaveRequestDto postSaveRequestDto, User user) {
        Category category = categoryRepository.findById(postSaveRequestDto.getCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));

        Post post = Post.builder()
                        .title(postSaveRequestDto.getTitle())
                        .content(postSaveRequestDto.getContent())
                        .category(category)
                        .user(user)
                        .build();

        postRepository.save(post);
    }

}
