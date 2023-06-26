package com.MyBlog.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
    @GetMapping("/api/board/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }
    @GetMapping("/api/boards")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

}
