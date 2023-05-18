package com.example.myblog.service;

import com.example.myblog.dto.ReplyDto;
import com.example.myblog.model.Board;
import com.example.myblog.model.Reply;
import com.example.myblog.model.User;
import com.example.myblog.repository.BoardRepository;
import com.example.myblog.repository.ReplyRepository;
import com.example.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long writeReply(User user, Long boardId, ReplyDto replyDto) throws Throwable {
        Board board = (Board) boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException());
        replyDto.setBoard(board);
        replyDto.setUser(user);
        Reply reply = replyDto.toEntity();
        return reply.getId();
    }

    @Transactional(readOnly = true)
    public List<ReplyDto> findAll(Long id) throws Throwable {
        Board board = (Board) boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        List<Reply> replyList = board.getReplyList();
        return replyList.stream().map(new ReplyDto()::toDto).collect(Collectors.toList());
    }


}
