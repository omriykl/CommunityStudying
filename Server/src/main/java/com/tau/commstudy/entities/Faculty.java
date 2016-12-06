package com.tau.commstudy.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="faculties")
@XmlRootElement
public class Faculty {
    @Id
//    @Column(name="FACULTY_ID")
    private int facultyId;
    
    private String facultyName;
   
    
    
    public Faculty() {
	
    }



    public int getId() {
        return facultyId;
    }



    public void setId(int id) {
        this.facultyId = id;
    }



    public String getFacultyName() {
        return facultyName;
    }



    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    
    
    
}
