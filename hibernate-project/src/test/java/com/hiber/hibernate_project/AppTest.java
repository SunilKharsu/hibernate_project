package com.hiber.hibernate_project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.hiber.hibernate_project.entity.Student;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
	
	StudentService studentService = new StudentService();
	@Test
	public void getStudentTest() {
		Student student = studentService.getById(3);
		System.out.println(student.getName());
		System.out.println(student.getCertificates().size());
		List<Student> list = studentService.getStudentByCollegeCriteria("The NorthCap University");
		System.out.println(list.size());
	}
   
}
