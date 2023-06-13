package com.MyBlog.project.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.MyBlog.project.model.User;
import com.MyBlog.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;
	
	//view에서 파라미터로 넘어온 username을 토대로 User 객체를 PrincipalDetails 객체에 저장하는 메소드
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
		//-> PrincipalDetails 객체가 DB에서 User정보를 가져와 필드로 저장한다.
		//-> PrincipalDetails가 리턴되면 Authentication 내부에 저장된다.
		// 그런 후 Authentication이 스프링 시큐리티의 session 내부에 들어가게 된다.
	}
}
