package io.github.scy256.blog.config.auth.provider.info;

import io.github.scy256.blog.config.auth.provider.OAuth2UserInfo;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf((long) attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) getProfile().get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) getAccount().get("email");
    }

    @Override
    public String getProfileImage() {
        return (String) getProfile().get("profile_image_url");
    }

    public Map<String, Object> getAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile() {
        return (Map<String, Object>) getAccount().get("profile");
    }

}
