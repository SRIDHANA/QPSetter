package com.sdhan.project.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "History")
public class History {
	
	private String hisdate;
	private int paper_no;
	private String questiontext;
	private String optiona;
	private String optionb;
	private String optionc;
	private String optiond;
	private String optione;
	private String correctanswer;
	public String getHisdate() {
		return hisdate;
	}
	public void setHisdate(String hisdate) {
		this.hisdate = hisdate;
	}
	public int getPaper_no() {
		return paper_no;
	}
	public void setPaper_no(int paper_no) {
		this.paper_no = paper_no;
	}
	public String getQuestiontext() {
		return questiontext;
	}
	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}
	public String getOptiona() {
		return optiona;
	}
	public void setOptiona(String optiona) {
		this.optiona = optiona;
	}
	public String getOptionb() {
		return optionb;
	}
	public void setOptionb(String optionb) {
		this.optionb = optionb;
	}
	public String getOptionc() {
		return optionc;
	}
	public void setOptionc(String optionc) {
		this.optionc = optionc;
	}
	public String getOptiond() {
		return optiond;
	}
	public void setOptiond(String optiond) {
		this.optiond = optiond;
	}
	public String getOptione() {
		return optione;
	}
	public void setOptione(String optione) {
		this.optione = optione;
	}
	public String getCorrectanswer() {
		return correctanswer;
	}
	public void setCorrectanswer(String correctanswer) {
		this.correctanswer = correctanswer;
	}
}
