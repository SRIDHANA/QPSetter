package com.sdhan.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdhan.project.model.Question;
import com.sdhan.project.service.QuestionSetService;

@Controller
public class UploadedQuestionsController {

	public QuestionSetService questionSetService;

	@Autowired(required = true)
	@Qualifier(value = "questionSetService")
	public void setQuestionSetService(QuestionSetService qs) {
		this.questionSetService = qs;
	}

	// Autowiring declarations above -actual code starts here

   //set value of listQuestions to loop and display on screen	
	@RequestMapping(value = "/viewQuestions", method = RequestMethod.GET)
	public String listQuestions(Model model) {
		model.addAttribute("Question", new Question());
		List<Question> list = this.questionSetService.listQuestions();
		model.addAttribute("listQuestions", list);
		return "viewQuestions";
	}
    
	//set remove method to delete question on screen
	@RequestMapping("/remove/{id}")
	public String removeQuestion(@PathVariable("id") int id) {
		this.questionSetService.removeQuestion(id);
		return "redirect:/viewQuestions";
	}


}
