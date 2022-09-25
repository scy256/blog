package io.github.scy256.blog.config.auth;

import io.github.scy256.blog.config.auth.account.SessionAccount;
import io.github.scy256.blog.config.auth.provider.OAuth2UserInfo;
import io.github.scy256.blog.config.auth.provider.OAuth2UserInfoFactory;
import io.github.scy256.blog.domain.blog.Blog;
import io.github.scy256.blog.domain.blog.BlogRepository;
import io.github.scy256.blog.domain.user.User;
import io.github.scy256.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final BlogRepository blogRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        User user = save(oAuth2UserInfo);
        generateBlog(user);
        User entity = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId()).get();

        httpSession.setAttribute("account", new SessionAccount(entity));

        return new CustomOAuth2User(entity, attributes);
    }

    private User save(OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())
                                    .map(entity -> entity.update(oAuth2UserInfo.getName(), oAuth2UserInfo.getProfileImage()))
                                    .orElse(oAuth2UserInfo.toEntity());
        return userRepository.save(user);
    }

    private void generateBlog(User user) {
        Optional<Blog> optionalBlog = blogRepository.findByUser(user);

        if(optionalBlog.isPresent()) return;

        Blog blog = Blog.builder()
                        .user(user)
                        .description("없음")
                        .build();

        blogRepository.save(blog);
    }

}

