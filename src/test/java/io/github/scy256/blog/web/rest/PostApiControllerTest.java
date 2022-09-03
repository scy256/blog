package io.github.scy256.blog.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scy256.blog.config.auth.CustomOAuth2User;
import io.github.scy256.blog.domain.category.Category;
import io.github.scy256.blog.domain.category.CategoryRepository;
import io.github.scy256.blog.domain.category.Topic;
import io.github.scy256.blog.domain.comment.Comment;
import io.github.scy256.blog.domain.comment.CommentRepository;
import io.github.scy256.blog.domain.post.Post;
import io.github.scy256.blog.domain.post.PostRepository;
import io.github.scy256.blog.domain.user.Role;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;
import io.github.scy256.blog.web.dto.comment.CommentSaveRequestDto;
import io.github.scy256.blog.web.dto.post.PostSaveRequestDto;
import io.github.scy256.blog.web.dto.post.PostUpdateRequestDto;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private CommentRepository commentRepository;

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

    @WithMockUser
    @Test
    public void testSavingPost() throws Exception{
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
                                                    .category("test")
                                                    .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        mvc.perform(post(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(postSaveRequestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        Post post = postRepository.findById(1L).get();

        assertThat(post.getTitle()).isEqualTo("test");
        assertThat(post.getTopic()).isEqualTo(Topic.NONE);
        assertThat(post.getUser().getId()).isEqualTo(1L);
        assertThat(post.getViews()).isEqualTo(0L);
    }

    @WithMockUser
    @Test
    public void testUpdatingPost() throws Exception {
        User user = userRepository.findById(1L).get();

        Category category = categoryRepository.save(
                Category.builder()
                        .name("test")
                        .user(user)
                        .topic(Topic.NONE)
                        .build()
        );

        postRepository.save(Post.builder()
                .title("test")
                .content("test")
                .user(user)
                .views(0L)
                .category(categoryRepository.findById(1L).get())
                .build()
        );


        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                                                                    .title("test1")
                                                                    .category("test")
                                                                    .content("test1")
                                                                    .build();

        String url = "http://localhost:" + port + "/api/v1/posts/1";

        mvc.perform(put(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(postUpdateRequestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        Post post = postRepository.findById(1L).get();

        assertThat(post.getTitle()).isEqualTo("test1");
        assertThat(post.getContent()).isEqualTo("test1");
    }

    @WithMockUser
    @Test
    public void testDeletingPost() throws Exception {
        User user = userRepository.findById(1L).get();

        Category category = categoryRepository.save(
                Category.builder()
                        .name("test")
                        .user(user)
                        .topic(Topic.NONE)
                        .build()
        );

        postRepository.save(Post.builder()
                .title("test")
                .content("test")
                .user(user)
                .views(0L)
                .category(categoryRepository.findById(1L).get())
                .build()
        );

        String url = "http://localhost:" + port + "/api/v1/posts/1";

        mvc.perform(delete(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        Optional<Post> post = postRepository.findById(1L);

        assertThat(post.isPresent()).isEqualTo(false);
    }

    @WithMockUser
    @Test
    public void testSavingComment() throws Exception {
        User user = userRepository.findById(1L).get();

        Category category = categoryRepository.save(
                Category.builder()
                        .name("test")
                        .user(user)
                        .topic(Topic.NONE)
                        .build()
        );

        postRepository.save(Post.builder()
                .title("test")
                .content("test")
                .user(user)
                .views(0L)
                .category(categoryRepository.findById(1L).get())
                .build()
        );

        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto("test");
        String url = "http://localhost:" + port + "/api/v1/posts/1/comments";

        mvc.perform(post(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(commentSaveRequestDto))
                    )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        assertThat(commentRepository.findById(1L).get().getContent()).isEqualTo("test");

    }

    @WithMockUser
    @Test
    public void testDeletingComment() throws Exception {
        User user = userRepository.findById(1L).get();

        Category category = categoryRepository.save(
                Category.builder()
                        .name("test")
                        .user(user)
                        .topic(Topic.NONE)
                        .build()
        );

        postRepository.save(Post.builder()
                .title("test")
                .content("test")
                .user(user)
                .views(0L)
                .category(categoryRepository.findById(1L).get())
                .build()
        );

        Comment comment = commentRepository.save(Comment.builder()
                .content("test")
                        .post(postRepository.findById(1L).get())
                .user(user)
                .build()
        );

        String url = "http://localhost:" + port + "/api/v1/posts/comments/1";

        mvc.perform(delete(url)
                        .with(oauth2Login()
                                .oauth2User(new CustomOAuth2User(userRepository.findById(1L).get(),new HashMap<>())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        assertThat(commentRepository.findById(1L).isPresent()).isEqualTo(false);

    }

}
