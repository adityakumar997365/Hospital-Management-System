package com.hms.HospitalManagementSystem.PatientRegisterModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class PatientRegisterController {
	
	@Autowired
	public PatientRegisterDAO patientRegisterDAO;

	
	@PostMapping("/patient")
	public String registerPatient(@ModelAttribute PatientRegisterDTO dto, Model model) {
        
		// Saves the matching form values through your DAO layer
		PatientRegisterModel patient = patientRegisterDAO.registerNewPatient(dto);
        
        model.addAttribute("registeredPatient", patient);
        
		// 3. Render the ThankYou page layout right after submission
		return "ThankYou";
	}
	
	@GetMapping("/check-request")
	public String showRequestTracker() {
		// Next Step Development Node: Build out the matching tracker interface page view here
		return "checkRequestStatus"; 
	}
	
	@GetMapping("/check-status-query")
	public String checkRegistrationStatus(@RequestParam(name = "phone", required = false) String contactNumber, Model model) 
	{
		
		// Input guard to handle empty search submissions gracefully
		if (contactNumber == null || contactNumber.trim().isEmpty()) {
			return "checkRequestStatus";
		}

		PatientRegisterModel patient = patientRegisterDAO.checkStatusOfApproval(contactNumber.trim());

		if (patient != null) {
			// Bind the query results to the template model variables
			model.addAttribute("patientName", patient.getName());
			
			// Normalizes the database text to uppercase to match your Thymeleaf conditions ('PENDING'/'APPROVED')
			if (patient.getRegistrationStatus() != null) {
				 String cleanStatusString = patient.getRegistrationStatus().name().trim().toUpperCase();
				 System.out.println(cleanStatusString);
				    model.addAttribute("patientSearchStatus", cleanStatusString);			} 
		} else {
			// Handles scenarios where the phone number does not exist in the database records
			model.addAttribute("patientSearchStatus", "NOT_FOUND");
		}

		return "checkRequestStatus";
		
	}
}