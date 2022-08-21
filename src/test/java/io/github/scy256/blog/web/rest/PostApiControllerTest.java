package io.github.scy256.blog.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scy256.blog.config.auth.CustomOAuth2User;
import io.github.scy256.blog.config.auth.SessionUser;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.post.PostRepository;
import io.github.scy256.blog.domain.user.Role;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import io.github.scy256.blog.web.dto.PostSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @WithMockUser
    @Test
    public void testSavingPost() throws Exception{
        userRepository.save(new User("test","test","test","test","test", Role.USER));
        User user = userRepository.findById(1L).get();

        Category category = categoryRepository.save(
                Category.builder()
                        .name("test")
                        .user(user)
                        .topic(Topic.NONE)
                        .build()
        );

        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                                                    .title("test")
                                                    .content("test")
                                                    .categoryId(1L)
                                                    .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        mvc.perform(post(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>()))
                        )
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(postSaveRequestDto)

                ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        Post post = postRepository.findById(1L).get();

        assertThat(post.getTitle()).isEqualTo("test");
        assertThat(post.getTopic()).isEqualTo(Topic.NONE);
        assertThat(post.getUser().getId()).isEqualTo(1L);

    }

}
