package io.github.scy256.blog.domain.blog;

import io.github.scy256.blog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByUser(User user);

}
