package io.github.scy256.blog.web.dto;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.util.AuthenticationUtils;

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
public class CategorySaveRequestDto {

    @Size(max = 50)
    @NotBlank
    private String name;

    private String topic;

    public Category toEntity() {
        return Category.builder()
                        .name(getName())
                        .topic(Topic.findByTitle(topic))
                        .user(AuthenticationUtils.getUserFromAuthentication())
                        .build();
    }

}
