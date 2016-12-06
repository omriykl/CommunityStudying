package com.tau.commstudy.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="test")
@XmlRootElement
public class Test {
    private long id;
    private int courseId;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private int facultyId;
    private int year;
    private char semester;
    private String teacher;
    private char numOfquestions;
    private char difficulty;
    
    
    public Test() {
	
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public int getCourseId() {
        return courseId;
    }


    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public int getFacultyId() {
        return facultyId;
    }


    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }


    public char getSemester() {
        return semester;
    }


    public void setSemester(char semester) {
        this.semester = semester;
    }


    public String getTeacher() {
        return teacher;
    }


    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public char getNumOfquestions() {
        return numOfquestions;
    }


    public void setNumOfquestions(char numOfquestions) {
        this.numOfquestions = numOfquestions;
    }


    public char getDifficulty() {
        return difficulty;
    }


    public void setDifficulty(char difficulty) {
        this.difficulty = difficulty;
    }
    
    

}
