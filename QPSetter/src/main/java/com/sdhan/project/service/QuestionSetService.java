package com.sdhan.project.service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sdhan.project.dao.QuestionDAO;
import com.sdhan.project.dao.QuestionSetDAO;
import com.sdhan.project.model.ChapterWtge;
import com.sdhan.project.model.Complexity;
import com.sdhan.project.model.Question;
import com.sdhan.project.model.QuestionSet;

public class QuestionSetService {

	private QuestionSetDAO questionSetDAO;

	@Autowired
	@Qualifier(value = "questionSetDAO")
	public void setQuestionSetDAO(QuestionSetDAO questionSetDAO) {
		this.questionSetDAO = questionSetDAO;
	}

	private QuestionDAO questionDAO;

	@Autowired
	@Qualifier(value = "questionDAO")	
	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	// Autowiring declarations above -actual code starts from here

	@Transactional
	public void addQuestionSet(QuestionSet qs) {
		this.questionSetDAO.addQuestionSet(qs);
	}

	@Transactional
	public void updateQuestionSet(QuestionSet qs) {
		this.questionSetDAO.updateQuestionSet(qs);
	}

	@Transactional
	public List<QuestionSet> listQuestionSets() {
		return this.questionSetDAO.listQuestionSets();
	}

	@Transactional
	public List<Question> listQuestions() {
		return this.questionDAO.listQuestions();
	}

	
	@Transactional
	public void removeQuestion(int id) {
		this.questionDAO.removeQuestion(id);
	}
	
	@Transactional
	public Question getQuestionById(int id) {
     	return	this.questionDAO.getQuestionById(id);
		
	}
	
	@Transactional
	public QuestionSet getQuestionSetById(int id) {
		return this.questionSetDAO.getQuestionSetById(id);
	}

	@Transactional
	public void removeQuestionSet(int id) {
		this.questionSetDAO.removeQuestionSet(id);
	}

	
	//logic to generate sets from the inputs in the browser
	@Transactional
	public void generateQuestionSets( String chapterWtgList, String simpleStr, String mediumStr,
			String hardStr, String noOfQuestionsStr, String noOfSetsStr) {

		float simpleFloat;
		float mediumFloat;
		float hardFloat;
		Integer simple = 0;
		Integer medium = 0;
		Integer hard = 0;
		Integer noOfQuestions = 0;
		Integer noOfSets = 0;
		float questionsno = 0;
		int intquestionsno = 0;
		int myCountNoOfQuestions = 0;
		float remaining, addupRemaining = 0;
		Integer chapno;
		int randomNum = 0;

		int length;
		int i;
		Random random;
		Question gotRandomQuestion;
		ChapterWtge chpwtobj;
		QuestionSet qs = null;

		try {
			noOfQuestions = Integer.valueOf(noOfQuestionsStr);
			noOfSets = Integer.valueOf(noOfSetsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<ChapterWtge> chpWtgListObj = new ArrayList<ChapterWtge>();

		ArrayList<Question> listOfQuestions;
		// mainloop to loop for nof sets entered
		for (int ino = 0; ino < noOfSets; ino++) {
			myCountNoOfQuestions = 0;
			qs = new QuestionSet();
			random = new Random();
			
			chpWtgListObj.clear();
			
			// get chapter weightage in stored in temp table like arraylist of chapterweightage object
			// Ex: chapterWtgesListStr == 1,20::2,40::3,30
			if (chapterWtgList != null) {
				String[] listofChpWtgs = chapterWtgList.split("::");
				for (String chpWtg : listofChpWtgs) {
					ChapterWtge chpwtg = new ChapterWtge();
					String[] chpWtgArr = chpWtg.split(",");
					chpwtg.setChapterNo(Integer.valueOf(chpWtgArr[0]));
					chpwtg.setWtge(Integer.valueOf(chpWtgArr[1]));

					// get the no of Questions caluculated from percentages to
					// number
					questionsno = (noOfQuestions * Float.valueOf(chpWtgArr[1])) / 100;

					// get the remaining percent of decimals after integer
					// questions
					remaining = questionsno - (int) questionsno;

					intquestionsno = (int) questionsno;

					addupRemaining = addupRemaining + remaining;

					if (addupRemaining > 1) {
						intquestionsno = intquestionsno + (int) addupRemaining;
					}

					chpwtg.setNoOfQuestions(intquestionsno);

					chpWtgListObj.add(chpwtg);
				}

			} // if ends of chp wtg list table

			//make the percentage of questions to real no of questions in paper
			simpleFloat = (noOfQuestions * Float.valueOf(simpleStr)) / 100;
			mediumFloat = (noOfQuestions * Float.valueOf(mediumStr)) / 100;
			hardFloat = (noOfQuestions * Float.valueOf(hardStr)) / 100;

			addupRemaining = 0;
			addupRemaining = simpleFloat - (int) simpleFloat;
			simple = (int) simpleFloat;

			addupRemaining = addupRemaining + mediumFloat - (int) mediumFloat;
			medium = (int) mediumFloat;

			addupRemaining = addupRemaining + hardFloat - (int) hardFloat;
			hard = (int) hardFloat;

			simple = simple + (int) addupRemaining;
			
            //get all the questions from database
			listOfQuestions = (ArrayList<Question>) questionDAO.listQuestions();

			for (ChapterWtge chp : chpWtgListObj) {

				if (chp.getNoOfQuestions() == 0 || chp.getNoOfQuestions() == null) {
					
					for (int j = 0 ; j<listOfQuestions.size();j++) {

						if (chp.getChapterNo() == listOfQuestions.get(j).getChapterNo()) {
							listOfQuestions.remove(listOfQuestions.get(j));
							j--;
							listOfQuestions.trimToSize();
						}
					}
				}
			}
			chpWtgListObj.trimToSize();
			gotRandomQuestion = new Question();
            
			//get no of queestions entered on screen
			while (myCountNoOfQuestions < noOfQuestions ) {

				length = listOfQuestions.size();

				try {
					System.out.println("mycount" + myCountNoOfQuestions + " length:" + length + listOfQuestions.size()
							+ " noofsets:" + ino);

					randomNum = random.nextInt(length);
				} catch (IllegalArgumentException e) {
					System.out.println("mycount" + myCountNoOfQuestions + " length:" + length + listOfQuestions.size()
							+ " noofsets:" + ino);
					e.printStackTrace();
					randomNum = random.nextInt(length);

				}
				try { //Generating a random number to pick a random question
					gotRandomQuestion = listOfQuestions.get(randomNum);
					System.out.println("setno:  " + ino + " questions no" + myCountNoOfQuestions + " chapterno:"
							+ gotRandomQuestion.getChapterNo() + " complexity:" + gotRandomQuestion.getComplexity()
							+ " simple counter:" + simple + " medium counter:" + medium + " hard counter:" + hard
							+ " currentsize:" + listOfQuestions.size());

				} catch (IndexOutOfBoundsException e) {
					System.out.println("mycount" + myCountNoOfQuestions);
					e.printStackTrace();
				}

				chapno = gotRandomQuestion.getChapterNo();
				chpwtobj = new ChapterWtge();

				for (ChapterWtge c : chpWtgListObj) {

					if (c.getChapterNo() != null && c.getChapterNo() == chapno) {
						chpwtobj = c;
						break;
					}

				}

				i = chpwtobj.getNoOfQuestions();
                
				//if chapter wieghtage in chapter weightage table is zero then exclude those questions from list so it not repeats
				if (i != 0) {
					chpwtobj.setNoOfQuestions(i - 1);
					listOfQuestions.remove(gotRandomQuestion);
					listOfQuestions.trimToSize();
					qs.getListOfQuestions().add(gotRandomQuestion);
					myCountNoOfQuestions = myCountNoOfQuestions + 1;

					if ((i - 1) == 0) {
						for (int j = 0;j< listOfQuestions.size(); j++) {
							if ( chpwtobj.getChapterNo() == listOfQuestions.get(j).getChapterNo()) {
								listOfQuestions.remove(listOfQuestions.get(j));
								j--;
								listOfQuestions.trimToSize();
							}
						}
					}
                    
					//Decrement the complexity counters so that if that complexity questions over then avoid that complexity questions from list 
					switch (gotRandomQuestion.getComplexity()) {
					case SIMPLE:
						simple = simple - 1;
						if (simple == 0) {
							for (int j = 0;j< listOfQuestions.size(); j++) {		
								if ( listOfQuestions.get(j).getComplexity() ==  Complexity.SIMPLE) {
									listOfQuestions.remove(listOfQuestions.get(j));
									j--;
									listOfQuestions.trimToSize();
								}
							}
						} // simple case if end

						break;

					case MEDIUM:

						medium = medium - 1;

						if (medium == 0)

						{
								for (int j = 0;j< listOfQuestions.size(); j++) {		
									if ( listOfQuestions.get(j).getComplexity() ==  Complexity.MEDIUM) {
										listOfQuestions.remove(listOfQuestions.get(j));
										j--;
										listOfQuestions.trimToSize();
									}
								}
						} // medium case if end
						
						break;

						case HARD:
						hard = hard - 1;
						if (hard == 0) {
							for (int j = 0;j< listOfQuestions.size(); j++) {		
								if ( listOfQuestions.get(j).getComplexity() ==  Complexity.HARD) {
									listOfQuestions.remove(listOfQuestions.get(j));
									j--;
									listOfQuestions.trimToSize();
								}
							}						} // hard case if end

						break;

					}// swtch end

				} // chapterno if end
				listOfQuestions.trimToSize();

			} // questions end

			
		
			qs.setCreatedOn(new Date());
			this.addQuestionSet(qs);
			
			listOfQuestions.clear();
			
		} // set end
		
	}// form end

}

