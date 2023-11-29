package com.job_finder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.job_finder.entity.UserDtls;
import com.job_finder.helperClass.EducationData;
import com.job_finder.helperClass.Employment;
import com.job_finder.helperClass.LoginForm;
import com.job_finder.helperClass.RegistrationForm;
import com.job_finder.repository.UserRepository;
import com.job_finder.response.LoginMessage;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserDtls> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserDtls> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public boolean createUser(UserDtls user) {
		userRepository.save(user);
		return true;
	}

	@Override
	public UserDtls updateUser(Long userId, UserDtls updatedUser) {
		Optional<UserDtls> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			UserDtls existingUser = optionalUser.get();
			existingUser.setFullName(updatedUser.getFullName());
			existingUser.setEmailId(updatedUser.getEmailId());
			existingUser.setMobileNumber(updatedUser.getMobileNumber());
			existingUser.setUsername(updatedUser.getUsername());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setDescription(updatedUser.getDescription());
			existingUser.setWorkStatus(updatedUser.getWorkStatus());
			existingUser.setResume(updatedUser.getResume());

			return userRepository.save(existingUser);
		} else {
			// Handle user not found
			return null;
		}
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public Optional<UserDtls> getUserDetailsById(Long userId) {
		Optional<UserDtls> optionalUser = userRepository.findById(userId);

		return optionalUser.map(user -> {
			UserDtls userDtls = new UserDtls();
			userDtls.setFullName(user.getFullName());
			userDtls.setEmailId(user.getEmailId());
			userDtls.setMobileNumber(user.getMobileNumber());
			userDtls.setUsername(user.getUsername());
			userDtls.setDescription(user.getDescription());
			userDtls.setWorkStatus(user.getWorkStatus());
			return userDtls;
		});
	}

	UserDtls user = new UserDtls();
	@Override
	@Transactional
	public String addEmployee(RegistrationForm form) {
		user.setFullName(form.getName());
		user.setEmailId(form.getEmailId());
		user.setMobileNumber(form.getMobileNumber());
		
		user.setPassword(this.passwordEncoder.encode(form.getPassword()));
		
		user.setWorkStatus(form.getWorkStatus());

		userRepository.save(user);
		return user.getFullName();
	}

	@Override
	public LoginMessage loginEmployee(LoginForm loginForm) {
		
		UserDtls user = userRepository.findByEmailId(loginForm.getEmail());
		if (user != null) {
			String password = loginForm.getPassword();
//			System.out.println(password);
			String encodedPassword = user.getPassword();
//			System.out.println(encodedPassword);
			Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
			if (isPwdRight) {
				Optional<UserDtls> usr = userRepository.findOneByEmailIdAndPassword(loginForm.getEmail(),
						encodedPassword);
				System.out.println(usr);
				if (usr.isPresent()) {
					return new LoginMessage("Login Success", true, user.getSrno());
				} else {
					return new LoginMessage("Login Failed", false, user.getSrno());
				}
			} else {

				return new LoginMessage("password Not Match", false, user.getSrno());
			}
		} else {
			return new LoginMessage("Email not exits", false, null);
		}
		
		
	}

	@Override
	public void saveEmploymentData(Employment employmentData) {
		UserDtls user = userRepository.findBySrno(employmentData.getSrno());
		if (user != null) {
			user.setEmployed(employmentData.isEmployed());
			user.setExperienceYears(employmentData.getExperienceYears());
			user.setExperienceMonths(employmentData.getExperienceMonths());
			user.setPrevCompany(employmentData.getPrevCompany());
			user.setPrevJob(employmentData.getPrevJob());
			user.setCity(employmentData.getCity());
			user.setAnnualSalary(employmentData.getAnnualSalary());			
		}
	}

	@Override
	public List<EducationData> getAllEducationDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EducationData getEducationDetailsById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEducationDetails(EducationData educationDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEducationDetails(Long id) {
		// TODO Auto-generated method stub
		
	}

	
//	EducationData 
//	@Override
//    public List<EducationData> getAllEducationDetails() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public EducationData getEducationDetailsById(Long id) {
//        Optional<EducationData> optionalEducationDetails = userRepository.findById(id);
//        return optionalEducationDetails.orElse(null);
//    }
//
//    @Override
//    public void saveEducationDetails(EducationData educationDetails) {
//    	userRepository.save(educationDetails);
//    }
//
//    @Override
//    public void deleteEducationDetails(Long id) {
//    	userRepository.deleteById(id);
//    }

}
