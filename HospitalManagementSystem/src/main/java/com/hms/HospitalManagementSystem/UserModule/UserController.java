package com.hms.HospitalManagementSystem.UserModule;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hms.HospitalManagementSystem.PatientModule.PatientModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientRepository;

@Controller
@RequestMapping("/forgot-password")
public class UserController {
	
	private final PatientRepository patientRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	

	public UserController(PatientRepository patientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@PostMapping("/verify")
    public String verifyUserIdentity(
            @RequestParam("mobile") String mobile,
            @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
            Model model) {
        
        // Query database for matching record
        Optional<PatientModel> userOptional = patientRepository.findByContactNumberAndDateOfBirth(mobile, dob);

        if (userOptional.isPresent()) {
            PatientModel user = userOptional.get();
            
            model.addAttribute("username", user.getContactNumber());
            // OPTION 1: Redirect to a dedicated separate "change password" view page
            return "change-password";
            
        } else {
            // IF NOT FOUND: Redirect back with error token to trigger your styled modal popup
            return "redirect:/forgot-password?error=notfound";
        }
    }
	
	  @PostMapping("/update")
	    public String updatePassword(
	            @RequestParam("username") String username,
	            @RequestParam("newPassword") String newPassword) {
	        
	        Optional<UserModel> userOptional = userRepository.findByUsername(username);

	        if (userOptional.isPresent()) {
	            UserModel user = userOptional.get();
	            
	            // Encrypts the raw password string using standard Spring Security protocols
	            user.setPassword(passwordEncoder.encode(newPassword));
	            
	            // Saves updated mapping directly back to database table
	            userRepository.save(user);
	            
	            // Returns back to layout, appending parameter that activates successModal popup box
	            return "redirect:/forgot-password/change-success?status=success";
	        }
	        
	        // Safety Fallback routing if field data manipulation occurs
	        return "redirect:/forgot-password?error=notfound";
	    }
	  
		// Helper mapping: Safely bridges the redirect parameters explicitly over into the Thymeleaf Model
		@GetMapping("/change-success")
		public String showChangeSuccessView(@RequestParam(value = "status", required = false) String status, Model model) {
			if (status != null) {
				// This forces the parameter directly into the view layout data layer
				model.addAttribute("status", status);
			}
			return "change-password";
		}


}
