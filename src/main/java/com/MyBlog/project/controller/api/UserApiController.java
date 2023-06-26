package com.MyBlog.project.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.MyBlog.project.dto.ResponseDto;
import com.MyBlog.project.dto.request.UserLoginRequestDto;
import com.MyBlog.project.dto.request.UserSaveRequestDto;
import com.MyBlog.project.dto.request.UserUpdateDto;
import com.MyBlog.project.model.RoleType;
import com.MyBlog.project.model.User;
import com.MyBlog.project.service.UserService;

import lombok.RequiredArgsConstructor;

/*
 * API Controller는 요청에 대한 객체 응답을 보낸다. 데이터 반환에 특화되어있다.
 * 객체는 데이터로 표현되기 쉬운 JSON, XML과 같은 형태로 결과를 출력한다.
 * Web API라고 하며 데이터로만 응답하는 HTTP 서비스를 생성하는데 사용된다.
 */
@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	@Autowired private final UserService userService;
	@Autowired private final AuthenticationManager authenticationManager;
	
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {
        userSaveRequestDto.setRole(RoleType.USER);
        userService.join(userSaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송 (JACKSON)
    }
    
    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody UserUpdateDto userUpdateDto) {
        User user = userUpdateDto.toEntity();
        userService.update(user);
        // 세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
