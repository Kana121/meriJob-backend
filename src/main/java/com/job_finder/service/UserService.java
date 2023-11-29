package com.job_finder.service;

import java.util.List;
import java.util.Optional;

import com.job_finder.entity.UserDtls;
import com.job_finder.helperClass.EducationData;
import com.job_finder.helperClass.Employment;
import com.job_finder.helperClass.LoginForm;
import com.job_finder.helperClass.RegistrationForm;
import com.job_finder.response.LoginMessage;

public interface UserService {
	List<UserDtls> getAllUsers();

    Optional<UserDtls> getUserById(Long userId);

    boolean createUser(UserDtls user);

    UserDtls updateUser(Long userId, UserDtls updatedUser);

    void deleteUser(Long userId);

    Optional<UserDtls> getUserDetailsById(Long userId);
    
    String addEmployee(RegistrationForm form);
    
   LoginMessage loginEmployee(LoginForm loginForm);
   
   
   String saveEmploymentData(Employment employmentData);
   
//   EducationService 
   List<EducationData> getAllEducationDetails();

   EducationData getEducationDetailsById(Long id);

   void saveEducationDetails(EducationData educationDetails);

   void deleteEducationDetails(Long id);
}
