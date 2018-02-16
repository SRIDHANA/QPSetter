package com.sdhan.project.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sdhan.project.model.QuestionSet;

@Repository
public class QuestionSetDAO {

	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	// Autowiring declarations above -actual code starts here
	public void addQuestionSet(QuestionSet qs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(qs);
	}

	public void updateQuestionSet(QuestionSet qs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(qs);
	}

	@SuppressWarnings("unchecked")
	public List<QuestionSet> listQuestionSets() {
		Session session = this.sessionFactory.getCurrentSession();
		List<QuestionSet> QuestionSetsList = session.createQuery("from QuestionSet").list();
		return QuestionSetsList;
	}

	public QuestionSet getQuestionSetById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		QuestionSet qs = (QuestionSet) session.load(QuestionSet.class, new Integer(id));
		return qs;
	}

	public void removeQuestionSet(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		QuestionSet qs = (QuestionSet) session.load(QuestionSet.class, new Integer(id));
		if (null != qs) {
			session.delete(qs);
		}
	}
}
