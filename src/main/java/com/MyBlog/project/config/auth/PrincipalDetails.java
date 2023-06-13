package com.MyBlog.project.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.MyBlog.project.model.User;

import lombok.Getter;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {
	
	private User user; //DB의 User 정보를 담을 필드
	public Map<String, Object> attributes; //OAuth 정보를 담을 필드
	
	//생성자-일반 로그인
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//생성자-OAuth 로그인
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	//내가 공부하던 시큐리티에서는 쓰지 않았음 확인할 필요 있다.
	public User getUser() {
        return user;
    }
	
	//== OAuth2User의 추상 메소드 구현 ==//
	
	//Attribute 속성값 반환
	@Override
	public Map<String, Object> getAttribute(String name) {
		return attributes;
	}
		
	//계정의 권한 목록 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(() -> { return "ROLE_" + user.getRole();} );
		
		return collection;
	}
	
	//== UserDetails의 추상 메소드 구현 ==//
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정 만료 여부 (true: 만료x)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정 잠김 여부 (true: 잠김x)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//비밀번호 만료 여부 (true: 만료x)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화 여부 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//어디에 쓰는지 모르겠는데 일단 사용하지 않아서 null임.
	@Override
	public String getName() {
		return null;
	}
}
