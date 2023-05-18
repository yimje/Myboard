package com.example.myblog.service;

import com.example.myblog.model.User;
import com.example.myblog.repository.BoardRepository;
import com.example.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUser(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
    }

    @Transactional(readOnly = true)
    public User findUSerById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다."));
    }



}
