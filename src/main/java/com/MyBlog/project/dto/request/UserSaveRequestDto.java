package com.MyBlog.project.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.MyBlog.project.model.RoleType;
import com.MyBlog.project.model.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserSaveRequestDto {
	
	@NotBlank
	@Size(min = 4, max = 12)
	@NotEmpty(message = "아이디 입력은 필수 입니다.")
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String gpassword2;
	
	@NotEmpty(message = "이메일 입력은 필수 입니다.")
	private String email;
	
	private RoleType role;
	
	@Builder
	public UserSaveRequestDto(String username, String password, String email, RoleType roleType) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = roleType;
	}
	
	public User toEntity() {
		return User.builder()
				   .username(username)
				   .password(gpassword2)
				   .email(email)
				   .role(role)
				   .build();
	}
}
