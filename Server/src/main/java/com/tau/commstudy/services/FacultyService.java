package com.tau.commstudy.services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tau.commstudy.beans.UserAllData;
import com.tau.commstudy.entities.Faculty;
import com.tau.commstudy.entities.University;
import com.tau.commstudy.entities.daos.FacultyDao;
import com.tau.commstudy.exceptions.UnauthorizesException;

@Service
public class FacultyService {

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private UniversityService universityService;

    /**
     * Creates and Saves to DB a Faculty entity.
     * 
     * @param Faculty
     *            object
     * @return the saved Faculty entity
     * @throws ValidationException
     *             if not saved
     */
    public Faculty add(Faculty faculty) throws ValidationException {
	return facultyDao.save(faculty);
    }

    /**
     * Retrieves an entity by its id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @return the saved Faculty entity
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public Faculty get(Long id) throws IllegalArgumentException {
	return facultyDao.findOne(id);
    }

    /**
     * Deletes an entity by its id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return true if deleted or not in DB
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public boolean delete(Long id) throws IllegalArgumentException {
	facultyDao.delete(id);
	return true;
    }

    /**
     * Adds to Faculty Entity A University entity that he belongs to.
     * 
     * @param Long
     *            id, University university
     * @return the saved Faculty entity
     * @throws ValidationException
     *             if id or universityId is null
     */
    public boolean addUniversity(Long id, Long universityId) throws ValidationException {
	Faculty faculty = get(id);
	University university = universityService.get(universityId);
	faculty.setUniversity(university);
	facultyDao.save(faculty);
	return true;
    }

    public UserAllData<Faculty> getUserAndAllData(String idTokenString) throws UnauthorizesException {
	UserAllData<Faculty> data = new UserAllData<>();
	data.setAllData(facultyDao.findAllByOrderByName());

	// User user = userService.get(idTokenString); // can throw
	// data.setUserData(userService.getAllFaculties());
	return data;

    }

}
