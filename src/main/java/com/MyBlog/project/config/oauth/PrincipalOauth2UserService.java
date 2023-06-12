package com.MyBlog.project.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.MyBlog.project.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;
	
	//OAuth로 부터 받은 userRequest 데이터에 대해서 후처리 되는 메소드
	//해당 메소드를 통해 PrincipalDetails 객체를 반환함으로써, Authentication 객체 안에 들어갈 수 있다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		return super.loadUser(userRequest);
	}
	
	
}
