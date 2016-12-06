package com.tau.commstudy.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="courses")
@XmlRootElement
public class Courses {
    
    @Id
    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private int facultyId;
    
   
    
    
    public Courses(){
	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    
}
