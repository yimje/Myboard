package com.MyBlog.project.dto;

import com.MyBlog.project.model.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
	
	private Long id;
	
	@NonNull // 이걸 왜 여기 붙이지
	private String title;
    private String content;
    private String category;
    private int views;
    
    public Board toEntity() {
    	return Board.builder()
    			.id(id)
    			.title(title)
    			.content(content)
    			.category(category)
    			.views(views)
    			.build();
    }
    
    //생성자
    public BoardDto toDto(Board board) {
    	this.id = board.getId();
    	this.title = board.getTitle();
    	 this.content = board.getContent();
         this.category = board.getCategory();
         this.views = board.getViews();
         return this;
    }
    
}
