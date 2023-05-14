package com.MyBlog.project.model;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.MyBlog.project.repository.BoardRepository;

@SpringBootTest
//@Transactional
public class BoardTest {
	
	@Autowired BoardRepository boardRepository;
	@Autowired EntityManager em;
	
	@AfterEach
    private void after(){
        em.clear();
    }
	
	@Test
	public void 게시판_성공() throws Exception {
		//given
		Board board = Board.builder().id(2L)
									 .title("게시글 한글 테스트")
									 .category("게시글 한글 테스트")
									 .content("게시글 한글 테스트")
									 .views(1)
									 .build();
		
		//when
		Board saveBoard = boardRepository.save(board);
		
		//than
		Board findBoard = boardRepository.findById(board.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));
		
//		assertThat(findBoard).isSameAs(board);
//		assertThat(findBoard).isSameAs(saveBoard);
		assertThat(findBoard).usingRecursiveComparison()
							 .ignoringFields("createdDate", "lastModifiedDate")
							 .isEqualTo(findBoard);
	}
}
