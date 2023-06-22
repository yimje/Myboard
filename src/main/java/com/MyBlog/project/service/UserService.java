package com.MyBlog.project.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.MyBlog.project.dto.request.UserSaveRequestDto;
import com.MyBlog.project.model.RoleType;
import com.MyBlog.project.model.User;
import com.MyBlog.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final BCryptPasswordEncoder encoder;
	
	//회원 가입 DB 저장
	@Transactional
	public void join(UserSaveRequestDto userSaveRequestDto) {
		if(userSaveRequestDto.getUsername().equals("damin")) {
			userSaveRequestDto.setRole(RoleType.ADMIN);
		}
		String encodePassword = encoder.encode(userSaveRequestDto.getPassword());
		userSaveRequestDto.setPassword(encodePassword);
		User user = userSaveRequestDto.toEntity();
		userRepository.save(user);
	}
	
	//회원 정보 DB 업데이트
	@Transactional
	public void update(User user) {
		User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("회원 없음"));
		
		if (findUser != null) {
			findUser.updateEmail(user.getEmail());
			findUser.updatePassword(user.getPassword());
			findUser.updateRole(user.getRole());
			
			userRepository.save(findUser);
			
			//서비스 단이라서 다시 저장해야할 것 같은데 github에는 안되어 있는데? 밑에 findUser 메소드도 쓰지 않았음.
		}
	}
}
