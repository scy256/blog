package io.github.scy256.blog.web.dto.category;

import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.user.User;
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

    @NotBlank
    private String topic;

    public Category toEntity() {
        User user = AuthenticationUtils.getUserFromAuthentication();

        return Category
                .builder()
                .name(getName())
                .topic(Topic.findByTitle(topic))
                .user(user)
                .build();
    }

}
