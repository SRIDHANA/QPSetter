package com.sdhan.project.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sdhan.project.dao.QuestionDAO;
import com.sdhan.project.dao.QuestionSetDAO;

import com.sdhan.project.model.Complexity;
import com.sdhan.project.model.Question;
import com.sdhan.project.model.QuestionSet;

public class FileService {
    
	private String tmpFolderPath = "C:\\Users\\Public\\Documents\\";

	private QuestionDAO questionDAO;

	@Autowired
	@Qualifier(value = "questionDAO")
	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	private QuestionSetDAO questionSetDAO;

	@Autowired
	@Qualifier(value = "questionSetDAO")
	public void setQuestionSetDAO(QuestionSetDAO questionSetDAO) {
		this.questionSetDAO = questionSetDAO;
	}

	// Autowiring declarations above -actual code starts here
    
	//Upload Questions file and pull & save it temporarily on local path and finally retrieve them and  update questions in database
	@Transactional
	public String processQuestionsFile(MultipartFile questionsFile) {
		if (questionsFile == null) {
			return "";
		}
		
		File file = null;
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Iterator<Row> iterator = null;
		Integer questionsFileId = null;
		FileOutputStream fos = null;
		try {
			try {
				file = new File(tmpFolderPath + "/" + "QuestionsFile_" + (new Date().getTime() * -1));
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(questionsFile.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					fos.close();
				}
			}

			questionsFileId = (int) (new Date().getTime());
			excelFile = new FileInputStream(file);
			workbook = new XSSFWorkbook(excelFile);
			datatypeSheet = workbook.getSheetAt(0); // Assumption that INput
													// file has only one Sheet
			iterator = datatypeSheet.iterator(); // Iterator for the Excel Rows
													// List of the Sheet

			int count = 0;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				count++;
				if (count == 1) { // Assuming the first row in the Excel Sheet
									// has Column Headers information, hence
									// ignoring first Excel Row
					continue;
				}

				// Check and ignore blank rows
				if (StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(0)))
						&& StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(1)))
						&& StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(2)))) {
					continue;
				}

				Integer chapterNoCellValue = Integer.valueOf(getCellValuesAsString(currentRow.getCell(0)));
				Integer bitNoCellValue = Integer.valueOf(getCellValuesAsString(currentRow.getCell(1)));
				String complexityCellValue = currentRow.getCell(2).getStringCellValue();
				String questionCellValue = currentRow.getCell(3).getStringCellValue();
				String optionACellValue = currentRow.getCell(4).getStringCellValue();
				String optionBCellValue = currentRow.getCell(5).getStringCellValue();
				String optionCCellValue = currentRow.getCell(6).getStringCellValue();
				String optionDCellValue = currentRow.getCell(7).getStringCellValue();
				String correctAnswerCellValue = currentRow.getCell(8).getStringCellValue();

				System.out.println("chapterNoCellValue: " + chapterNoCellValue + ",  " + "complexityCellValue: "
						+ complexityCellValue + ",  " + "questionCellValue: " + questionCellValue + ",  "
						+ "bitNoCellValue:" + bitNoCellValue + ",  " + "optionACellValue: " + optionACellValue + ",  "
						+ "optionBCellValue: " + optionBCellValue + ",  " + "optionCCellValue: " + optionCCellValue
						+ ",  " + "optionDCellValue: " + optionDCellValue + ",  " + "correctAnswerCellValue: "
						+ correctAnswerCellValue);

				Question question = new Question();
				question.setChapterNo(chapterNoCellValue);
				question.setBitNo(bitNoCellValue);
				question.setComplexity(Complexity.valueOf(complexityCellValue));
				question.setQuestiontext(questionCellValue);
				question.setOptiona(optionACellValue);
				question.setOptionb(optionBCellValue);
				question.setOptionc(optionCCellValue);
				question.setOptiond(optionDCellValue);
				question.setCorrectAnswer(correctAnswerCellValue);
			//	question.setQuesitonFileId(questionsFileId);
				questionDAO.addQuestion(question);

				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
					workbook = null;
				}
				if (excelFile != null) {
					excelFile.close();
					excelFile = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return (questionsFileId != null ? String.valueOf(questionsFileId) : "");
	}
    
	
	//Onclick upload button process chapter weightage file to get into temporarily later put in table
	@Transactional
	public String processChapterWtgeFile(MultipartFile chpWtgsFile) {
		if (chpWtgsFile == null) {
			return "";
		}
		File file = null;
		int i = 0;
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Iterator<Row> iterator = null;
		String chapterWtgesListStr = "";
		ArrayList<String> chapterWtgesList = new ArrayList<String>();
		FileOutputStream fos = null;
		try {
			try {
				file = new File(tmpFolderPath + "/" + "CHPWTGSFile_" + (new Date().getTime() * -1));
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(chpWtgsFile.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					fos.close();
				}
			}

			excelFile = new FileInputStream(file);
			workbook = new XSSFWorkbook(excelFile);
			datatypeSheet = workbook.getSheetAt(0); // Assumption that INput
													// file has only one Sheet
			iterator = datatypeSheet.iterator(); // Iterator for the Excel Rows
													// List of the Sheet

			int count = 0;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				count++;
				if (count == 1) { // Assuming the first row in the Excel Sheet
									// has Column Headers information, hence
									// ignoring first Excel Row
					continue;
				}

				// Check and ignore blank rows
				if (StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(0)))
						&& StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(1)))
						&& StringUtils.isBlank(getCellValuesAsString(currentRow.getCell(2)))) {
					continue;
				}

				Integer chapterNoCellValue = Integer.valueOf(getCellValuesAsString(currentRow.getCell(0)));
				Integer weightageCellValue = Integer.valueOf(getCellValuesAsString(currentRow.getCell(1)));
				i = i + (int) weightageCellValue;

				System.out.println("chapterNoCellValue: " + chapterNoCellValue + ",  " + "weightageCellValue: "
						+ weightageCellValue);
				chapterWtgesList.add(chapterNoCellValue + "," + weightageCellValue);
				System.out.println();
			}
			if (i != 100) {
				return "Sum not 100";
			}
			chapterWtgesListStr = StringUtils.join(chapterWtgesList, "::");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
					workbook = null;
				}
				if (excelFile != null) {
					excelFile.close();
					excelFile = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return chapterWtgesListStr;
	}
   
	// Helper Methods for File Processing
		@SuppressWarnings("deprecation")
		private String getCellValuesAsString(Cell cell) {
			String returnValue = null;
			if (cell.getCellTypeEnum() == CellType.STRING) {
				returnValue = cell.getStringCellValue();
			} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				returnValue = String.valueOf((int) cell.getNumericCellValue());
			}
			return returnValue;
		}

	//Download Question paper
	@Transactional
	public void downloadQuestionSetFile(HttpServletResponse response, int questionSetId) {
		QuestionSet qs = this.questionSetDAO.getQuestionSetById(questionSetId);
		System.out.println("QS Id: " + qs.getId() + ", createdOn: " + qs.getCreatedOn() );

		InputStream inputStream = null;
		File file = null;
	
		FileOutputStream fout = null;
	
		try {
			// Generating Word file
					
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph tmpParagraph = document.createParagraph();
			XWPFRun tmpRun = tmpParagraph.createRun();
			file = new File(tmpFolderPath + "/" + "QSN_SET_" + qs.getId() + "_" + new Date().getTime() + ".docx");
			file.createNewFile();
			
			int i = 1;	
			tmpRun.setText("		Exam Paper			");
			tmpRun.addBreak();
			tmpRun.addBreak();
			
			for (Question q : qs.getListOfQuestions()) {
				tmpRun.setText(i + " " +q.getQuestiontext() );
				tmpRun.addBreak();
				tmpRun.setText( q.getOptiona());
				tmpRun.addBreak();
				tmpRun.setText(q.getOptionb());
				tmpRun.addBreak();
				tmpRun.setText(q.getOptionc());
				tmpRun.addBreak();
				tmpRun.setText(q.getOptiond());
				tmpRun.addBreak();
				tmpRun.addBreak();
				i++;
			}
			tmpRun.setFontSize(18);
			fout = new FileOutputStream(file);
			document.write(fout);
			document.close();
			fout.close();
			System.out.println("Word written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fout != null) {
					fout.close();
				}
				}
			 catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Writing Excel file to Output to be download on Browser
		try {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				mimeType = "application/msword";
				
			response.setContentType(mimeType);
			/*
			 * "Content-Disposition : inline;" will show file content in browser
			 * "Content-Disposition : attachment;" will download the file
			 */
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			inputStream = new BufferedInputStream(new FileInputStream(file));

			// Copy bytes from source to destination(outputstream in this
			// example), closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
}