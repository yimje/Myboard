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
		return http.csrf(csrf -> csrf.disable())
				   .httpBasic(httpBasic -> httpBasic.disable())
				   
				   //인증 권한 설정. 현재 아래 경로 외에 권한이 필요하다고만 되어 있음.
				   .authorizeHttpRequests(authroize -> authroize
						   .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll()
						   .anyRequest()
						   .authenticated())
				   
				   //기본 로그인 설정
				   .formLogin(fromLogin -> fromLogin.loginPage("/auth/loginForm")
						   .loginProcessingUrl("/auth/login")
						   .defaultSuccessUrl("/"))
				   
				   //Oauth2 로그인 설정
			       .oauth2Login(oauth2Login -> oauth2Login.loginPage("/auth/loginForm")
			    		   .userInfoEndpoint()
			    		   .userService(principalOauth2UserService))
			       .build();
	}
}
