package io.github.scy256.blog.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scy256.blog.config.auth.CustomOAuth2User;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.user.Role;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import io.github.scy256.blog.web.dto.category.CategorySaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userRepository.save(new User("test","test","test","test","test", Role.USER));
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @WithMockUser
    @Test
    public void testSavingCategory() throws Exception{
        User user = userRepository.findById(1L).get();
        CategorySaveRequestDto categorySaveRequestDto = CategorySaveRequestDto.builder()
                                                            .name("test")
                                                            .topic("스포츠")
                                                            .build();
        String url = "http://localhost:" + port + "/api/v1/categories";

        mvc.perform(post(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(categorySaveRequestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        Category category = categoryRepository.findById(1L).get();

        assertThat(category.getName()).isEqualTo("test");
        assertThat(category.getTopic()).isEqualTo(Topic.SPORTS);
        assertThat(category.getUser().getId()).isEqualTo(user.getId());
    }

}
