package com.hiber.hibernate_project;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;


import com.hiber.hibernate_project.entity.Student;
import com.hiber.hibernate_project.hiberUtil.HibernateUtil;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class StudentService {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	//save student
	public void saveStudent(Student student) {
		try(Session session = sessionFactory.openSession()) {
			Transaction beginTransaction = session.beginTransaction();
			session.persist(student);
			beginTransaction.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//get student by student_id
	public Student getById(long studentId) {
		try(Session session = sessionFactory.openSession()){
			Transaction transaction = session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			transaction.commit();
			return student;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//update student information
	public Student updateStudent(long studentId, Student student) {
		try(Session session = sessionFactory.openSession()){
			Transaction beginTransaction = session.beginTransaction();
			Student oldStudent = session.get(Student.class, studentId);
			if(oldStudent != null) {
				oldStudent.setName(student.getName());
				oldStudent.setFathersName(student.getFathersName());
				oldStudent.setCollege(student.getCollege());
				oldStudent.setPhone(student.getPhone());
				oldStudent.setAbout(student.getAbout());
				oldStudent = session.merge(oldStudent);
				
			}
			beginTransaction.commit();
			return oldStudent;
		}
		
	}
	
	//delete student
	public Student deleteSudent(long studentId) {
		try(Session session = sessionFactory.openSession()){
			Transaction transaction = session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			if(student != null) {
				session.remove(student);
			}
			transaction.commit();
			if(student != null)
				return student;
			
		}
		return null;
	}
	
	//HQL[JPA]--> native query
	//database independent
	
	//get all student using hql
	public List<Student> getAllStudentsHQL(){
		try(Session session = sessionFactory.openSession()){
			String getHQL = "FROM student";
			Query<Student> query = session.createQuery(getHQL, Student.class);
			return query.list();
		}
	}
	
	//get by student name
	
	public Student getStudentByNameHQL(String name) {
		try(Session session = sessionFactory.openSession()){
			String getHQL = "FROM student WHERE name = :studentName";
			Query<Student> query = session.createQuery(getHQL, Student.class);
			query.setParameter("studentName", name);
			return query.uniqueResult();
		}
	}
	
	//criteria api
	//get all student of same college
	public List<Student> getStudentByCollegeCriteria(String college){
		
		try(Session session = sessionFactory.openSession()){
			
			HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			
			CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
			
			Root<Student> root = query.from(Student.class);
			
			query.select(root).where(criteriaBuilder.equal(root.get("college"), college));
			
			Query<Student> query2 = session.createQuery(query);
			
			return query2.getResultList();
			
		}
	}
	
	public List<Student> getStudentWithPagination(int pageNo, int pageSize){
		try(Session session = sessionFactory.openSession()){
			String pagiQuery = "FROM student";
			Query<Student> query = session.createQuery(pagiQuery, Student.class);
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.list();
			
		}
	}
	

}
