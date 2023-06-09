package com.MyBlog.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.MyBlog.project.dto.Response.UserResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false, length = 100)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	private String provider;
	private String providerId;
	
	public void updateEmail(String email) {
		this.email = email;
	}
	
	public void updatePassword(String password) {
		this.password = password;
	}
	
	public void updateRole(RoleType role) {
		this.role = role;
	}
	
    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
    
    @Builder
    public User(long id, String username, String password, String email, RoleType role, String provider, String providerId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
