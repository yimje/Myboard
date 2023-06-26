package com.MyBlog.project.config.oauth;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.MyBlog.project.config.auth.PrincipalDetails;
import com.MyBlog.project.config.oauth.provider.FacebookUserInfo;
import com.MyBlog.project.config.oauth.provider.GoogleUserInfo;
import com.MyBlog.project.config.oauth.provider.KakaoUserInfo;
import com.MyBlog.project.config.oauth.provider.NaverUserInfo;
import com.MyBlog.project.config.oauth.provider.OAuth2UserInfo;
import com.MyBlog.project.model.RoleType;
import com.MyBlog.project.model.User;
import com.MyBlog.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	/* Oauth를 통해 받은 User의 정보를 가지고 회원 가입을 강제로 진행하는 service임.
	 * 또한 User의 정보(이름, 이메일, 권한 등)을 PrincipalDetails에 담아서 Session에 저장함 
	 */
	
	//순환참조를 방지하지 위해서 @RequiredArgsConstructor을 사용을 권장한다. 이는 final 필드에 대해서 생성자를 통한 주입을 시켜준다.
	//@Autowired는 지양해야할 습관이다.
	private final UserRepository userRepository;
	private final PasswordEncoder encoderPwd;
	
	//OAuth로 부터 받은 userRequest 데이터에 대해서 후처리 되는 메소드
	//해당 메소드를 통해 PrincipalDetails 객체를 반환함으로써, Authentication 객체 안에 들어갈 수 있다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		String loginProvider = userRequest.getClientRegistration().getRegistrationId();
		
		OAuth2UserInfo oauth2UserInfo = oauth2UserInfoCreate(loginProvider, oauth2User);
		
		String provider = oauth2UserInfo.getProvider();
		String providerId = oauth2UserInfo.getProviderID();
		String username = provider+"_"+providerId;
		//OAuth 로그인은 비밀번호를 저장하지 않음
		String password = encoderPwd.encode("Oauth2 Loging User");
		String email = oauth2UserInfo.getEmail();
		//String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity != null) {
			System.out.println("이미 회원입니다. 이전에 OAuth로 회원가입을 진행했습니다.");
		} else {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(RoleType.USER)
					.provider(providerId)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	
	}
	
	private OAuth2UserInfo oauth2UserInfoCreate(String loginProvider, OAuth2User oauth2User) {
		OAuth2UserInfo oauth2UserInfo = null;
		
		if(loginProvider.equals("google")) {
			System.out.println("구글 로그인을 진행합니다.");
			oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
			
		} else if(loginProvider.equals("facebook")) {
			System.out.println("페이스북 로그인을 진행합니다.");
			oauth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
			
		} else if(loginProvider.equals("naver")){
			System.out.println("네이버 로그인을 진행합니다.");
			oauth2UserInfo = new NaverUserInfo((Map<String, Object>)oauth2User.getAttributes().get("response"));
			
		} else if(loginProvider.equals("kakao")){
			System.out.println("카카오 로그인을 진행합니다.");
			oauth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
			
		} else {
			System.out.println("지원하지 않는 로그인입니다.");
		}
		
		return oauth2UserInfo;
	}
	
	
}
