package com.MyBlog.project.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncodeConfig {
	
	//PaswwordEncoder 순환참조를 방지하기 위해서 따로 만들어진 설정 클래스이다.
	//따라서 SecurityConfig에서 생성하는 PasswordEncoder @Bean 메소드는 삭제한다.
	//PasswordEncoder을 인터페이스 타입으로 Bean을 등록해 느슨한 결합을 추구했다.
	//따라서 BCryptPasswordEncoder을 사용하는 클래스는 PasswordEnocder 타입으로 주입받으면
	//실제 주입받는 객체는 BCryptPassworedEncoder이 된다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
