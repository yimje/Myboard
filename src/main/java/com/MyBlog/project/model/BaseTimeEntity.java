package com.MyBlog.project.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
	
	@CreatedDate
	@Column(updatable = false)
	//@Temporal(TemporalType.TIMESTAMP) // 시, 분, 초까지만 저장함, 사용하지 않으면 밀리초까지 저장
	private String createDate;
	
	@LastModifiedDate
	@Column(updatable = false)
	//@Temporal(TemporalType.TIMESTAMP) 
	private String modifedDate;
	
	//날짜의 포맷 형식을 지정하는 메소드의 역할을 없애고, 어노테이션으로 변경함.
	
}
