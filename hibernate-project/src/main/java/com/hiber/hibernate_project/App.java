package com.hiber.hibernate_project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hiber.hibernate_project.entity.Certificate;
import com.hiber.hibernate_project.entity.Student;
import com.hiber.hibernate_project.hiberUtil.HibernateUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println(sessionFactory);
        
        Student student = new Student();
        student.setName("Sunil Kharsu");
        student.setFathersName("Satpal Kharsu");
        student.setCollege("The NorthCap University");
        student.setPhone("9518005924");
        student.setAbout("Engineering Student");
        student.setActive(true);
        
        Certificate certi = new Certificate();
        certi.setTitle("Java certificate");
        certi.setLink("link");
        certi.setAbout("This is for Java course completion certificate");
        certi.setStudent(student);
        
        Certificate certi1 = new Certificate();
        certi1.setTitle("Python certificate");
        certi1.setLink("link");
        certi1.setAbout("This is for Python course completion certificate");
        certi1.setStudent(student);
        
        student.getCertificates().add(certi1);
        student.getCertificates().add(certi);
        
        Session session = sessionFactory.openSession();
        
        Transaction transaction = null;
        
        try {
        	transaction = session.beginTransaction();
        	session.persist(student);
        	transaction.commit();
        	
        	System.out.println("Successfully student saved");
        }
        catch(Exception e) {
        	if(transaction != null) {
        		transaction.rollback();
        	}
        	
        	e.printStackTrace();
        	
        }
        
        finally {
    		session.close();
    	}
        
    }
}
