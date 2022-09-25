package io.github.scy256.blog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByBlogId(Long blogId, Pageable pageable);

    Page<Post> findAllByBlogIdAndCategoryName(Long blogId, String categoryName, Pageable pageable);

    Page<Post> findAllByTitleContainingAndBlogId(String title,Long blogId, Pageable pageable);

}
