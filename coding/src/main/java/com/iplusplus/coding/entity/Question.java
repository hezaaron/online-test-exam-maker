package com.iplusplus.coding.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity @Data
@ToString(exclude={"quiz"})
@EqualsAndHashCode(exclude= {"quiz"})
@RequiredArgsConstructor
public final class Question implements Serializable {

	private static final long serialVersionUID = 6292560590697622204L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "quiz_id")
    private final Quiz quiz;
    private final String name;
    private final String code;
    @Column(nullable = false)
    private boolean multiAnswer = false;
	
    public Question() {
    	this(null, null, null);
    }
}