package com.sdhan.project.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sdhan.project.model.Question;

@Repository
public class QuestionDAO {

	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	
	// Autowiring declarations above -actual code starts here

	public void addQuestion(Question q) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(q);
	}

	public void updateQuestion(Question q) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(q);
	}

	@SuppressWarnings("unchecked")
	public List<Question> listQuestions() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Question> QuestionsList = session.createQuery("from Question").list();
		return QuestionsList;
	}
	

	public Question getQuestionById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Question q = (Question) session.load(Question.class, new Integer(id));
		return q;
	}

	public void removeQuestion(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Question q = (Question) session.load(Question.class, new Integer(id));
		if (null != q) {
			session.delete(q);
		}
	}
}