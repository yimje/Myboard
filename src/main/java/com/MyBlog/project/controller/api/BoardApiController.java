package com.MyBlog.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.MyBlog.project.config.auth.PrincipalDetails;
import com.MyBlog.project.dto.BoardDto;
import com.MyBlog.project.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
	
	@Autowired
	private final BoardService boardService;

	@PostMapping("/api/board")
	public ResponseEntity write(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetails principalDetails ) {
		return ResponseEntity.ok(boardService.write(boardDto, principalDetails.getUser()));
	}

}
