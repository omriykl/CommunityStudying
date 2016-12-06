package com.tau.commstudy.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="tagsToTestQuestions")
@XmlRootElement
public class TagToTestQuestion {

    private long id;
    private long testQuestionId;
    private long tagId;
    public TagToTestQuestion() {
	
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getTestQuestionId() {
        return testQuestionId;
    }
    public void setTestQuestionId(long testQuestionId) {
        this.testQuestionId = testQuestionId;
    }
    public long getTagId() {
        return tagId;
    }
    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

}
