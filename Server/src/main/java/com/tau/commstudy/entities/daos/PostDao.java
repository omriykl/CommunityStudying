package com.tau.commstudy.entities.daos;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.tau.commstudy.entities.Course;
import com.tau.commstudy.entities.Post;
import com.tau.commstudy.entities.Test;
import com.tau.commstudy.entities.TestQuestion;
import com.tau.commstudy.entities.User;

public interface PostDao extends CrudRepository<Post, Long>, QueryDslPredicateExecutor<Post> {

    public Iterable<Post> findByUser(User user);

    public List<Post> findByTestQuestion_Test_CourseOrderByTimeDesc(Course course);

    public List<Post> findByTestQuestion_Test_YearAndTestQuestion_Test_SemesterAndTestQuestion_Test_MoedAndTestQuestion_Test_CourseOrderByTimeDesc(
	    Integer year, Character semester, Character moed, Course course);

    public List<Post> findByTestQuestionOrderByTimeDesc(TestQuestion testQuestion);

    public List<Post> findByTestQuestion_TestOrderByTimeDesc(Test test);

    public List<Post> findByTestQuestion_Test_CourseAndTestQuestion_Test_YearOrderByTimeDesc(Course course, Integer year);

    public List<Post> findByTestQuestion_Test_YearAndTestQuestion_Test_SemesterAndTestQuestion_Test_CourseOrderByTimeDesc(
	    Integer year, Character semester, Course course);

    public List<Post> findByTestQuestion_Test_CourseInOrderByTimeDesc(Set<Course> course, Pageable page);

    public Long countByTestQuestion_Test_CourseInOrderByTimeDesc(Set<Course> course);
}
