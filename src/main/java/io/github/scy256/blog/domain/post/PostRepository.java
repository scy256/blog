package io.github.scy256.blog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    List<Post> findAllByUserId(Long userId);

}
