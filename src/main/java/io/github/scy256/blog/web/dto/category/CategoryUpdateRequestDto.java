package io.github.scy256.blog.web.dto.category;

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
public class CategoryUpdateRequestDto {

    @Size(max = 50)
    @NotBlank
    private String name;

    @NotBlank
    private String topic;

}
