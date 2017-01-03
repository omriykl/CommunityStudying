package com.tau.commstudy.beans;

public class PostCriteria {

    private Long facultyId;
    private Long courseId;
    private Integer year;
    private Character semester;
    private Character moed;
    private Integer questionNumber;

    public PostCriteria() {
    }

    public Long getFacultyId() {
	return facultyId;
    }

    public void setFacultyId(Long facultyId) {
	this.facultyId = facultyId;
    }

    public Long getCourseId() {
	return courseId;
    }

    public void setCourseId(Long courseId) {
	this.courseId = courseId;
    }

    public Integer getYear() {
	return year;
    }

    public void setYear(Integer year) {
	this.year = year;
    }

    public Character getSemester() {
	return semester;
    }

    public void setSemester(Character semester) {
	this.semester = semester;
    }

    public Character getMoed() {
	return moed;
    }

    public void setMoed(Character moed) {
	this.moed = moed;
    }

    public Integer getQuestionNumber() {
	return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
	this.questionNumber = questionNumber;
    }

}