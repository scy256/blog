package io.github.scy256.blog.web.view;

import io.github.scy256.blog.domain.user.Role;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private TestRestTemplate testRestTemplate;

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
    }

    @Test
    public void testIndex() {
        String body = testRestTemplate.getForObject("/", String.class);
        assertThat(body).contains("당신의 일상을 기록하세요");
    }

    @Test
    @WithMockUser
    public void testBlog() throws Exception {
        userRepository.save(new User("test","test","test","test","test", Role.USER));
        mvc.perform(get("/blog/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/blog"));
    }

}
