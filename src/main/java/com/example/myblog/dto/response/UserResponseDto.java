package com.example.myblog.dto.response;

import com.example.myblog.model.RoleType;
import com.example.myblog.model.User;
import lombok.Builder;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserResponseDto {

    private Long id;
    @NotBlank
    @Size(min =4, max = 12)
    @NotEmpty(message = "아이디 입력은 필수 입니다.")
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private RoleType role;

    @Builder
    public UserResponseDto(Long id, String username, String password, String email, RoleType role){
        this.id       = id;
        this.username = username;
        this.password = password;
        this.email    = email;
        this.role     = role;
    }

    public User toDto() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
