package io.github.scy256.blog.config.auth.provider.info;

import io.github.scy256.blog.config.auth.provider.OAuth2UserInfo;

import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo{

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("picture");
    }

}
