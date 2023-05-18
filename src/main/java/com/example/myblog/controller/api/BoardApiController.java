package com.example.myblog.controller.api;

import com.example.myblog.config.auth.PrincipalDetails;
import com.example.myblog.dto.BoardDto;
import com.example.myblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sun.net.ftp.FtpDirEntry;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;
    @PostMapping("/api/board")
    public ResponseEntity write(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        return ResponseEntity.ok(boardService.write(boardDto, String.valueOf(principalDetails.getUser())));
    }

    @GetMapping("api/board/{id}")
    public ResponseEntity findyId(@PathVariable Long id) throws Throwable {
        return ResponseEntity.ok(boardService.findById(id));
    }
}