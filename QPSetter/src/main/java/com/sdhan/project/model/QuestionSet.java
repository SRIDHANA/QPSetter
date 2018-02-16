package com.sdhan.project.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "QuestionSet")
public class QuestionSet {
	private Integer id;
	
	private Date createdOn;
	private Set<Question> listOfQuestions = new HashSet<Question>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

/*
	public Integer getQuestionSetNo() {
		return questionSetNo;
	}

	public void setQuestionSetNo(Integer questionSetNo) {
		this.questionSetNo = questionSetNo;
	}
*/
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@OneToMany
	@JoinTable(name = "questionSet_to_question", 
			   joinColumns = @JoinColumn(name = "questionSetId"), 
			   inverseJoinColumns = @JoinColumn(name = "questionId"))
	public Set<Question> getListOfQuestions() {
		return listOfQuestions;
	}

	public void setListOfQuestions(Set<Question> listOfQuestions) {
		this.listOfQuestions = listOfQuestions;
	}
	
	
}