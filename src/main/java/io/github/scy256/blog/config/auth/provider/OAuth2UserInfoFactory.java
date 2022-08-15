package io.github.scy256.blog.config.auth.provider;

import io.github.scy256.blog.config.auth.provider.info.GoogleUserInfo;
import io.github.scy256.blog.config.auth.provider.info.KakaoUserInfo;
import io.github.scy256.blog.config.auth.provider.info.NaverUserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equals("google"))
            return new GoogleUserInfo(attributes);
        if(registrationId.equals("kakao"))
            return new KakaoUserInfo(attributes);
        if(registrationId.equals("naver"))
            return new NaverUserInfo((Map<String, Object>) attributes.get("response"));

        return null;
    }

}
