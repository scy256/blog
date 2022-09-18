package io.github.scy256.blog.service;

import io.github.scy256.blog.domain.comment.Comment;
import io.github.scy256.blog.domain.comment.CommentRepository;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.post.PostRepository;
import io.github.scy256.blog.handler.exception.EntityNotFoundException;
import io.github.scy256.blog.util.AuthenticationUtils;
import io.github.scy256.blog.web.dto.comment.CommentResponseDto;
import io.github.scy256.blog.web.dto.comment.CommentSaveRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다"));
        Comment comment = Comment.builder()
                .content(commentSaveRequestDto.getContent())
                .post(post)
                .user(AuthenticationUtils.getUserFromAuthentication())
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));
        return comment.getUser().getId() == AuthenticationUtils.getUserFromAuthentication().getId();
    }

}
