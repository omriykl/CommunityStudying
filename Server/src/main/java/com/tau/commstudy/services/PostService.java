package com.tau.commstudy.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tau.commstudy.beans.NewPostBean;
import com.tau.commstudy.beans.PostCriteria;
import com.tau.commstudy.entities.Course;
import com.tau.commstudy.entities.Post;
import com.tau.commstudy.entities.Test;
import com.tau.commstudy.entities.TestQuestion;
import com.tau.commstudy.entities.User;
import com.tau.commstudy.entities.daos.PostDao;
import com.tau.commstudy.exceptions.UnauthorizesException;

@Service
public class PostService {

    @Autowired
    private PostDao dao;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestQuestionService questionService;

    @Autowired
    private UserService userService;

    public Post createPost(@RequestBody Post newPost) {
	try {
	    newPost.setTime(Calendar.getInstance());
	    System.out.println("6");
	    return dao.save(newPost);
	} catch (Exception ex) {
	    System.out.println("Error creating the post:" + ex.toString());
	    return null; // "Error creating the post:" + ex.toString();
	}

	// return "Post succesfully saved!";
    }

    public String delete(Long id) {
	if (id == null)
	    return "long parameter 'id' needs to be provided";

	try {
	    dao.delete(id);
	} catch (Exception ex) {
	    return "Error deleting the post:" + ex.toString();
	}
	return "Post succesfully deleted!";
    }

    /**
     * if the parameter was not provided, returns corresponding message
     */
    @RequestMapping(value = "/delete", params = {})
    public String delete() {
	return "long parameter 'id' needs to be provided";
    }

    /**
     * increases the number of votes by 1
     */
    public String like(long id) {
	try {
	    Post post = dao.findOne(id);
	    post.setVotes(post.getVotes() + 1);
	    dao.save(post);
	} catch (Exception ex) {
	    return "Error updating votes for post: " + ex.toString();
	}
	return "Post succesfully updated!";
    }

    public Iterable<Post> getAll() {
	return dao.findAll();
    }

    /**
     * This updates only the content, title of the post. All the other fields
     * (including id and time) stay the same
     */
    public String updatePost(Post givenPost) {
	try {
	    Post post = dao.findOne(givenPost.getId());
	    post.setContent(givenPost.getContent());
	    post.setTitle(givenPost.getTitle());
	    dao.save(post);
	} catch (Exception ex) {
	    return "Error updating the post: " + ex.toString();
	}
	return "Post succesfully updated!";
    }

    public Post getById(long id) {
	return dao.findOne(id);
    }

    public Iterable<Post> getByUser(User user) {
	return dao.findByUser(user);
    }

    public List<Post> getByCourse(Course course) {
	return dao.findByTestQuestion_Test_CourseOrderByTimeDesc(course);
    }

    public List<Post> getByTest(Test test) {
	return dao.findByTestQuestion_TestOrderByTimeDesc(test);
    }

    public List<Post> getByYear(Course course, Integer year) {
	return dao.findByTestQuestion_Test_CourseAndTestQuestion_Test_YearOrderByTimeDesc(course, year);
    }

    private List<Post> getBySemester(Course course, Integer year, Character semester) {
	return dao.findByTestQuestion_Test_YearAndTestQuestion_Test_SemesterAndTestQuestion_Test_CourseOrderByTimeDesc(
		year, semester, course);
    }

    public List<Post> getByMoed(Course course, Integer year, Character semester, Character moed) {
	Test test = testService.getByMoed(course, year, semester, moed);
	return getByTest(test);
    }

    public List<Post> getByTestAndNumber(Test test, Integer number) {
	TestQuestion question = questionService.getByTestAndNumber(test, number);
	return getByTestQuestion(question);
    }

    public boolean checkByMoedK(Integer year, Character semester, Character moed, Course course) {
	List<Post> posts = dao
		.findByTestQuestion_Test_YearAndTestQuestion_Test_SemesterAndTestQuestion_Test_MoedAndTestQuestion_Test_CourseOrderByTimeDesc(
			year, semester, moed, course);
	if (posts.size() == 0)
	    return false;
	return true;
    }

    /**
     * check if posts for this test moed already exist
     *
     * @param bean
     * @return TRUE or FALSE
     */

    public boolean checkByMoed(PostCriteria criteria) {
	Course course = courseService.get(criteria.getCourseId());
	List<Post> posts = dao
		.findByTestQuestion_Test_YearAndTestQuestion_Test_SemesterAndTestQuestion_Test_MoedAndTestQuestion_Test_CourseOrderByTimeDesc(
			criteria.getYear(), criteria.getSemester(), criteria.getMoed(), course);
	if (posts == null || posts.isEmpty())
	    return false;
	return true;
    }

    /**
     * finds all posts connected to a specific testQuestion.
     *
     * @param TestQuestion
     * @return List of posts
     */
    public List<Post> getByTestQuestion(TestQuestion question) {
	return dao.findByTestQuestionOrderByTimeDesc(question);
    }

    /**
     * finds all posts connected to a question. If no question number given,
     * return by test
     *
     * @param criteria
     *            the used fields are: -course -year -semester -moed
     *            -questionNumber
     * @return List of posts
     */
    public List<Post> search(PostCriteria criteria) {
	// if only year given - find posts for this year's tests
	Course course = courseService.get(criteria.getCourseId());

	if (criteria.getMoed() == null) {
	    if (criteria.getSemester() == null) {
		if (criteria.getYear() == null) {
		    // return by course
		    return getByCourse(course);
		}
		// return by year
		return getByYear(course, criteria.getYear());
	    }
	    // return by semester
	    return getBySemester(course, criteria.getYear(), criteria.getSemester());
	}

	// return by test
	Test test = testService.getByMoed(course, criteria.getYear(), criteria.getSemester(), criteria.getMoed());
	if (test == null) // no such test in the table
	    return null;

	if (criteria.getQuestionNumber() == null) // if no number was given
	    return getByTest(test);

	return getByTestAndNumber(test, criteria.getQuestionNumber());
    }

    public boolean checkByQuestion(PostCriteria criteria) {
	if (search(criteria) == null)
	    return false;

	return true;
    }

    public Post addNewPost(NewPostBean bean, String userTokenId) throws UnauthorizesException {
	// 1. check if such test exists
	// 2. if not - create
	// 3. check if such testQuestion exists
	// 4. if not - create
	// 5. create post related to testQuestion

	User user = userService.get(userTokenId);
	if (user == null)
	    return null;

	Course course = courseService.get(bean.getCourseId());
	Test test = testService.getByMoed(course, bean.getYear(), bean.getSemester(), bean.getMoed());
	if (test == null) // no such test in the table
	{
	    test = new Test();
	    test.setCourse(course);
	    test.setYear(bean.getYear());
	    test.setSemester(bean.getSemester());
	    test.setMoed(bean.getMoed());
	    test = testService.add(test);
	}

	if (bean.getQuestionNumber() == null) // if no number was given
	    bean.setQuestionNumber(0);

	TestQuestion question = questionService.getByTestAndNumber(test, bean.getQuestionNumber());
	if (question == null) {
	    question = new TestQuestion();
	    question.setQuestionNumber(bean.getQuestionNumber());
	    question.setTest(test);
	    question = questionService.add(question);
	}

	Post post = new Post();
	post.setTitle(bean.getTitle());
	post.setContent(bean.getContent());
	// post.setTags(bean.getTags());
	post.setTestQuestion(question);
	post.setTime(Calendar.getInstance());
	post.setUser(user);

	return createPost(post);
    }

}