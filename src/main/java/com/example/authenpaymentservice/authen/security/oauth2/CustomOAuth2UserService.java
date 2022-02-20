package com.example.authenpaymentservice.authen.security.oauth2;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.AuthProvider;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.model.CustomUserDetails;
import com.example.authenpaymentservice.authen.repository.UserRepository;
import com.example.authenpaymentservice.authen.security.oauth2.user.OAuth2UserInfo;
import com.example.authenpaymentservice.authen.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.OAuth2AuthenticationProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String authProvider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());
        if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException(Message.OAUTH2_EMAIL_NOT_FOUND);
        }

        User user = userRepository.findUserByEmail(oAuth2UserInfo.getEmail());
        if (Objects.nonNull(user)) {
            user = user;
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setState(UserState.ACTIVE);
        user.setAuthProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setEmail(oAuth2UserInfo.getEmail());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(existingUser);
    }
}
