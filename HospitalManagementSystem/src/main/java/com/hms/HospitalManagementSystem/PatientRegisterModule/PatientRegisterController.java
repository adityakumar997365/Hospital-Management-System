package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.HospitalManagementSystem.PatientRegisterModule.PatientRegisterModel.RegistrationStatus;

@Controller
@RequestMapping("/register")
public class PatientRegisterController {

	@Autowired
	private PatientRegisterServices patientRegisterServices;

	@Autowired
	private PatientRegisterRepository patientRegisterRepository;

	//check existence of patient using ajax
	@GetMapping("/check-phone")
	@ResponseBody
	public boolean checkPhoneExists(@RequestParam("phone") String phone) {
		// Returns true if the phone number already exists, false otherwise
		return patientRegisterRepository.existsByContactNumber(phone);
	}

	// Check existence of patient phone number during an profile update
	@GetMapping("/check-phone-update")
	@ResponseBody // Returns raw true/false directly to jQuery
	public boolean checkPhoneExistsForUpdate(@RequestParam("phone") String phone) {
	    // Returns true if the phone number already exists in the database, false otherwise
	    return patientRegisterRepository.existsByContactNumber(phone);
	}

	@PostMapping("/patient")
	public String registerPatient(@ModelAttribute PatientRegisterDTO dto, Model model) {

		// Saves the matching form values through your DAO layer
		PatientRegisterModel patient = patientRegisterServices.registerNewPatient(dto);

		model.addAttribute("registeredPatient", patient);

		// 3. Render the ThankYou page layout right after submission
		return "ThankYou";
	}
	//page view
	@GetMapping("/check-request")
	public String showRequestTracker() {
		// Next Step Development Node: Build out the matching tracker interface page
		// view here
		return "checkRequestStatus";
	}

	@GetMapping("/check-status-query")
	public String checkRegistrationStatus(@RequestParam(name = "phone", required = false) String contactNumber,
			Model model) {

		// Input guard to handle empty search submissions gracefully
		if (contactNumber == null || contactNumber.trim().isEmpty()) {
			return "checkRequestStatus";
		}

		PatientRegisterModel patient = patientRegisterServices.checkStatusOfApproval(contactNumber.trim());

		if (patient != null) {
			// Bind the query results to the template model variables
			model.addAttribute("patientName", patient.getName());

			// Normalizes the database text to uppercase to match your Thymeleaf conditions
			// ('PENDING'/'APPROVED')
			if (patient.getRegistrationStatus() != null) {
				String cleanStatusString = patient.getRegistrationStatus().name().trim().toUpperCase();
				System.out.println(cleanStatusString);
				model.addAttribute("patientSearchStatus", cleanStatusString);
				
				if(cleanStatusString.equals("REJECTED"))
				{
					model.addAttribute("rejectionReason", patient.getReason());
				}
			}
		} else {
			// Handles scenarios where the phone number does not exist in the database
			// records
			model.addAttribute("patientSearchStatus", "NOT_FOUND");
		}

		return "checkRequestStatus";

	}
	
	//update the rejected data
	
	// 1. GET: Fetch existing data and display the edit page
	@GetMapping("/update-details")
	public String showUpdateForm(@RequestParam("phone") String phone, Model model) {
	    // Find the existing record by phone number
	    Optional<PatientRegisterModel> patient = patientRegisterRepository.findByContactNumber(phone);
	    
	    if (patient.isEmpty()) {
	        model.addAttribute("errorMessage", "No registration record found for this phone number.");
	        return "checkRequestStatus"; // Redirect or show error on tracking page
	    }
	    
	    // Bind the existing data object directly to the template model
	    model.addAttribute("patientData", patient.get());
	    return "updatePatientRegistration"; // Your new edit form HTML file name
	}

	// 2. POST: Process the form submission and save changes
	@PostMapping("/update-submit")
	public String processUpdateDetails(@ModelAttribute("patientData") PatientRegisterModel updatedData, RedirectAttributes redirectAttributes) {
	    // Look up the persistent entity to preserve its primary key ID and existing status
	    PatientRegisterModel existingPatient = patientRegisterRepository.findById(updatedData.getPatientRegisterId()).orElse(null);
	    
	    if (existingPatient != null) {
	        // Map updated form values back to the managed database record
	        existingPatient.setName(updatedData.getName());
	        existingPatient.setContactNumber(updatedData.getContactNumber());
	        existingPatient.setDateOfBirth(updatedData.getDateOfBirth());
	        existingPatient.setAddress(updatedData.getAddress());
	        // Reset the registration status back to PENDING so the admin can review the corrections
	        existingPatient.setRegistrationStatus(RegistrationStatus.PENDING);
	        existingPatient.setReason(null); // Clear the previous rejection reason
	        
	        // Save back into the exact same row
	        patientRegisterRepository.save(existingPatient);
	        
	        redirectAttributes.addFlashAttribute("successMessage", "Registration details updated successfully and resubmitted for approval!");
	    }
	    
	    return "redirect:/register/check-request?phone=" + updatedData.getContactNumber();
	}

}