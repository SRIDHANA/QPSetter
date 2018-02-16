package com.sdhan.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sdhan.project.service.FileService;
import com.sdhan.project.service.QuestionSetService;

@Controller
public class QuestionPaperSetterController {
	
	public FileService fileService;
	
	@Autowired(required = true)
	@Qualifier(value = "fileService")
	public void setFileService(FileService fs) {
		this.fileService = fs;
	}

	public QuestionSetService questionSetService;

	@Autowired(required = true)
	@Qualifier(value = "questionSetService")
	public void setQuestionSetService(QuestionSetService qs) {
		this.questionSetService = qs;
	}
	
	// Autowiring declarations above -actual code starts here

	//Return jsp page name to view for first url entered 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectIndexPage() {
		return "questionPapterSetter";
	}

	//on Clicking Downloadset - download the previous sets 
		@RequestMapping(value = "/questionset/download/{id}", method = RequestMethod.GET)
		public void downloadQuestionSet(HttpServletResponse response, @PathVariable("id") int id) {
			this.fileService.downloadQuestionSetFile(response, id);
		}
		
	//on jsp page handle inputs for uploading and generate sets
	@RequestMapping(value = "/qpsetsprocessor/process", method = RequestMethod.POST)
	public String QuestionPaperSetsProcessor(@RequestParam("questionFile") MultipartFile questionsFile,
											 @RequestParam("chpWtgFile") MultipartFile chpWtgFile, 
											 MultipartHttpServletRequest request,
											 RedirectAttributes redirectAttributes) 
													 throws IllegalStateException, IOException 
	{
		
				
		String simple = StringUtils.defaultIfBlank(request.getParameter("simple"), "");
		String medium = StringUtils.defaultIfBlank(request.getParameter("medium"), "");
		String hard = StringUtils.defaultIfBlank(request.getParameter("hard"), "");
		String noOfQuestions = StringUtils.defaultIfBlank(request.getParameter("noOfQuestions"), "");		
		String noOfSets = StringUtils.defaultIfBlank(request.getParameter("noOfSets"), "");
		String questionFilePath = StringUtils.defaultIfBlank(request.getParameter("questionFilePath"), "");
		String questionsFileId = StringUtils.defaultIfBlank(request.getParameter("questionsFileId"), "");
		String chpWtgFilePath = StringUtils.defaultIfBlank(request.getParameter("chpWtgFilePath"), "");
		String chapterWtgesListStr = StringUtils.defaultIfBlank(request.getParameter("chapterWtgesListStr"), "");
		String action = StringUtils.defaultIfBlank(request.getParameter("action"), "");
		String errorMsg = "true";

	
		if ("UPLOAD_QSTNS_FILE".equals(action)) { //Upload Question file
			questionsFileId = this.fileService.processQuestionsFile(questionsFile);
			errorMsg = "";
		}
		else if ("UPLOAD_CHP_WTGS_FILE".equals(action)) { // upload weightage file
		
			chapterWtgesListStr = this.fileService.processChapterWtgeFile(chpWtgFile);
			if (chapterWtgesListStr == "Sum not 100")
			{
				errorMsg = "Sum of percentages in Chapter weightage file should be 100 . Please check the file";
				
			}
			else{
			errorMsg = "";
			}
			}
		else if ("GENERATE_QNS_SETS".equals(action)) {  //Generate sets button
			int percent ;
			percent = (int)(Integer.valueOf(simple)) + (int)(Integer.valueOf(medium)) +(int)(Integer.valueOf(hard));
			if (percent != 100 )
			{
				errorMsg = "Please enter simple + medium + hard complexity percentage to 100";				
			}
			else
			{
			if (!(this.questionSetService.listQuestions().isEmpty()) && !StringUtils.isBlank(chapterWtgesListStr)) {
				// check if questionsFileId is not null/empty and chapterWtgesListStr is not null or empty
				// Ex: chapterWtgesListStr == 1,20::2,40::3,30
				    	
				 this.questionSetService.generateQuestionSets(chapterWtgesListStr,simple,medium,hard,noOfQuestions,noOfSets);
					errorMsg = "Executed Successfully";
				
			} else {
				errorMsg = "Please check and upload your files for uploading";
			}
			}
		}
       
		//let values be hold on same page even after refresh
		redirectAttributes.addAttribute("simple", simple);
		redirectAttributes.addAttribute("medium", medium);
		redirectAttributes.addAttribute("hard", hard);
		redirectAttributes.addAttribute("noOfQuestions", noOfQuestions);
		redirectAttributes.addAttribute("noOfSets", noOfSets);
		redirectAttributes.addAttribute("questionFilePath", questionFilePath);
		redirectAttributes.addAttribute("chpWtgFilePath", chpWtgFilePath);
		redirectAttributes.addAttribute("questionsFileId", questionsFileId);
		redirectAttributes.addAttribute("chapterWtgesListStr", chapterWtgesListStr);
		redirectAttributes.addAttribute("errorMsg", errorMsg);

		return "redirect:/";
	}
	
}