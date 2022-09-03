package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.post.PostRepository;;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.util.AuthenticationUtils;
import io.github.scy256.blog.web.dto.post.PostResponseDto;
import io.github.scy256.blog.web.dto.post.PostSaveRequestDto;

import io.github.scy256.blog.web.dto.post.PostUpdateRequestDto;
import io.github.scy256.blog.web.dto.post.PostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findAllByUserId(Long userId, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByUserId(userId, pageable);
        Page<PostResponseDto> dto = posts.map(post -> new PostResponseDto(post));
        return new PostsResponseDto(dto);
    }

    @Transactional
    public void save(PostSaveRequestDto postSaveRequestDto) {
        Category category = categoryRepository.findByName(postSaveRequestDto.getCategory())
                            .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
        Post post = Post.builder()
                        .title(postSaveRequestDto.getTitle())
                        .content(postSaveRequestDto.getContent())
                        .views(0L)
                        .category(category)
                        .user(AuthenticationUtils.getUserFromAuthentication())
                        .build();
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        Category category = categoryRepository.findByName(postUpdateRequestDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent(), category);
    }

    @Transactional
    public void increaseViews(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        post.increaseViews();
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Long id) {
        User user = AuthenticationUtils.getUserFromAuthentication();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        return post.getUser().getId() == user.getId();
    }

}
