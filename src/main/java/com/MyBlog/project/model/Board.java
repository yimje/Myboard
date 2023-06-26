package com.MyBlog.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.MyBlog.project.dto.BoardDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = false)
	private String category;
	
	@Lob // 대용량 데이터
	private String content;
	
	//조회수
	@Column(nullable =false)
	private int views;
	
	@ManyToOne(fetch = FetchType.LAZY) //EAGER=호출시 바로 로드
    @JoinColumn(name = "userId") //DB상 필드값은 userId로 설정
    private User user;
	
	public void updateBoard(String title, String content, String category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}
	
	public BoardDto toDto() {
		return BoardDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .views(views)
                .user(user)
                .build();
	}
}
