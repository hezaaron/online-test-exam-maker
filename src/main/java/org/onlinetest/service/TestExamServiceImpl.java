package org.onlinetest.service;

import java.util.List;

import org.onlinetest.dao.TestExamDao;
import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.entity.TestExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testExamService")
@Transactional
public class TestExamServiceImpl implements TestExamService {

	@Autowired
	private TestExamDao testExamDao;
	
	@Override
	public TestExam findExamByName(String name) {
		return testExamDao.findExamByName(name);
	}
	
	@Override
	public String getExamName(int examId) {
		return testExamDao.getExamName(examId);
	}
	
	@Override
	public String getExamDescription(int id) {
		return testExamDao.getExamDescription(id);
	}
	
	@Override
	public List<Question> getQuestions(int examId) {
		return testExamDao.getQuestions(examId);
	}
	
	@Override
	public List<QuestionChoice> getQuestionChoices(int examId) {
		return testExamDao.getQuestionChoices(examId);
	}

	@Override
	public List<QuestionChoice> getCorrectChoices(int examId, boolean isCorrect) {
		return testExamDao.getCorrectChoices(examId, isCorrect);
	}
}