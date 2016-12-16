package com.tau.commstudy.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.tau.commstudy.entities.TestQuestion;
import com.tau.commstudy.entities.daos.TestQuestionDao;

@RestController
@RequestMapping("/question")
public class QuestionController {
   
    @Autowired
    private TestQuestionDao dao;
    
    @RequestMapping(method = RequestMethod.GET, value = "/save")
    public String saveQuestion(String title,String content,String userId)
    {
	TestQuestion q = new TestQuestion();
	q.setTitle(title);
	q.setUserId(userId);
	q.setContent(content);
	dao.save(q);
	
	return "";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String saveByPost(@RequestBody TestQuestion q)
    {
	dao.save(q);
	
	return "";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getById")
    public TestQuestion get(long id)
    {
	return dao.findOne(id);
    }
    
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Iterable<TestQuestion> getAll()
    {
	return dao.findAll();
    }
}
