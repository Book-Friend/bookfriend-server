package com.book.config.oauth;

import java.util.Map;
import java.util.Optional;

import com.book.config.auth.PrincipalDetails;
import com.book.config.oauth.provider.*;
import com.book.domain.user.User;
import com.book.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		OAuth2UserInfo oAuth2UserInfo = null;
		String userId = userRequest.getClientRegistration().getRegistrationId();
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		} else if(userId.equals("kakao")){
			oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("properties"),
					Long.toString((Long) oAuth2User.getAttributes().get("id")));
		}

		Optional<User> userOptional =
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		User user = null;
		if (!userOptional.isPresent()) {
			user = User.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		} else {
			user = userOptional.get();
		}

		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
