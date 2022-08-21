package io.github.scy256.blog.web.dto;

import io.github.scy256.blog.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    @Size(max = 100)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long categoryId;

}
