package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        return new UserResponseDto(user);
    }

}
