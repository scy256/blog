package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.blog.Blog;
import io.github.scy256.blog.domain.blog.BlogRepository;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.web.dto.blog.BlogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional(readOnly = true)
    public BlogResponseDto findById(Long id) {
        Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("블로그를 찾을 수 없습니다"));
        return new BlogResponseDto(blog);
    }

}

