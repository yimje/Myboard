package com.MyBlog.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.MyBlog.project.config.oauth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // Security 필터로 등록 = Security 설정을 해당 클래스에서 한다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // Spring Security 활성화 / 해당 메소드 실행 전 체크함.
@RequiredArgsConstructor
public class SecurityConfig {
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().httpBasic().and()
			.authorizeHttpRequests()
			.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll() //해당 경로는 어떤 경로든 접근할 수 있음.
			.anyRequest() //위의 설정한 경로 외에 모든 경로를 아래에서 설정한다.
			.authenticated() //인증된 사용자만이 접근 가능하도록 설정함.
			.and().formLogin() //form 기반의 로그인을 사용
			.loginPage("/auth/loginForm") // 로그인 페이지의 view를 설정함.
			.loginProcessingUrl("/auth/login") // 로그인을 처리할 URL 지정, 기본값은 /login
			.defaultSuccessUrl("/") //로그인 성공시 redirect될 URL
			.and()
			//여기서부턴 OAuth2 설정
			.oauth2Login()
			.loginPage("/auth/loginForm") //기본 로그인과 동일한 페이지에서 하도록 설정
			.userInfoEndpoint()
			.userService(principalOauth2UserService); 
		return http.build();
	}

}
