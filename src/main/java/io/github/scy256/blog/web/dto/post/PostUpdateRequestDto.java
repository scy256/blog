package io.github.scy256.blog.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {

    @Size(max = 100)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

}
