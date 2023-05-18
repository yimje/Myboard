package com.example.myblog.service;


import com.example.myblog.dto.BoardDto;
import com.example.myblog.model.Board;
import com.example.myblog.repository.BoardRepository;
import com.example.myblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long write(BoardDto boardDto, String user) {
        boardDto.setUser(user);
        Board saveBoard = boardDto.toEntity();
        boardRepository.save(saveBoard);
        return saveBoard.getId();
    }

    @Transactional(readOnly = true)// 읽기전용 -> 상태변화X -> 영속성 컨택스트 관리 X
    public Page<Board> list(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> categoryList(Pageable pageable, String category) {
        return boardRepository.findByCategory(pageable, category);
    }
    @Transactional(readOnly = true) // 읽기 전용 -> 상태변화X->영속성 컨텍스트 관리X
    public Board details(Long id) throws Throwable {
        return (Board) boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("아이디를 찾을 수 없습니다");
        });
    }

    @Transactional(readOnly = true) // 읽기 전용 -> 상태변화X->영속성 컨텍스트 관리X
    public BoardDto findById(Long id) throws Throwable {
        Board board = (Board) boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다 id= " + id));
        return board.toDto();
    }

    @Transactional
    public int updateViews(Long id) {
        return boardRepository.updateViews(id);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, BoardDto requestBoardDto) throws Throwable {
        Board board = (Board) boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글을 찾을 수 없습니다");
        }); // -> 영속화 완료
        board.updateBoard(requestBoardDto.getTitle(), requestBoardDto.getContent(), requestBoardDto.getCategory());
        // 바로 값만 새로 세팅해주면 된다
        // 해당 함수 종료시 트랜잭션 종료되고 더티체킹 후 플러시(자동 업데이트)
    }

    public List<BoardDto> findAll() {
        BoardDto boardDto = new BoardDto();
        return (List<BoardDto>) boardRepository.findAll().stream().map(board -> boardDto.toDto((Board) board)).collect(Collectors.toList());
    }

}
