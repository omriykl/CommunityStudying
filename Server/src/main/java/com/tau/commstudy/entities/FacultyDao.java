package com.tau.commstudy.entities;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface FacultyDao extends CrudRepository<Faculty, Long> {

    

}
