package com.sdhan.project.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sdhan.project.service.QuestionSetService;

@Controller
public class HistoryController {

	public QuestionSetService questionSetService;
	
	@Autowired(required = true)
	@Qualifier(value = "questionSetService")
	public void setQuestionSetService(QuestionSetService qs) {
		this.questionSetService = qs;
	}
	
	// Autowiring declarations above -actual code starts here
	
	//Return history jsp page
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String QuestionPaperSetsProcessor(Model model) throws IllegalStateException, IOException {
		
		model.addAttribute("questionSetsList", this.questionSetService.listQuestionSets());
		return "history";
	
	}

}
