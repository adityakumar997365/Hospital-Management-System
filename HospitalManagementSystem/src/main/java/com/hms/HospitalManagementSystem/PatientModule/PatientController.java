package com.hms.HospitalManagementSystem.PatientModule;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentServices;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorRepository;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorServices;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberModel;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberRepository;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionModel;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionServices;


@Controller
@RequestMapping("/patient")
public class PatientController {

	private final PatientServices patientServices;
	private final DoctorServices doctorservices;
	private final DoctorRepository doctorRepository;
	private final PrescriptionServices prescriptionServices;
	private final AppointmentServices appointmentServices;
	private final FamilyMemberRepository familyMemberRepository;
	

	public PatientController(PatientServices patientServices, DoctorServices doctorservices,
			DoctorRepository doctorRepository, PrescriptionServices prescriptionServices,
			AppointmentServices appointmentServices, FamilyMemberRepository familyMemberRepository) {

		this.patientServices = patientServices;
		this.doctorservices = doctorservices;
		this.doctorRepository = doctorRepository;
		this.prescriptionServices = prescriptionServices;
		this.appointmentServices = appointmentServices;
		this.familyMemberRepository = familyMemberRepository;
	}

	@GetMapping("/dashboard")
	public String showPatientDashboard(Model model, Principal principal) {

		// for patient
		String usernameMob = principal.getName();

		PatientModel patient = patientServices.getUserByContact(usernameMob);

		model.addAttribute("patientName", patient.getName());

		model.addAttribute("patientDetails", patient);
		
		//for doctor details
		List<DoctorModel> doctorsList = doctorservices.getAllDoctor();
		
		model.addAttribute("doctorsList",doctorsList);
		
		// for prescription histroy
		
		List<PrescriptionModel> pateintprescriptionList = prescriptionServices.getPatientHistory(patient.getPatientId());
		
		// Sort by date in descending order (Newest/Latest dates first)
				if (pateintprescriptionList != null) {
					pateintprescriptionList.sort((a1, a2) -> a2.getCreatedDate().compareTo(a1.getCreatedDate()));
				}

		
		model.addAttribute("pateintprescriptionList", pateintprescriptionList);
		
		// for appointment history
		
		List<AppointmentModel> patientAppointmentHistoryList = appointmentServices.getAllAppointmentsByPatientId(patient.getPatientId());
		
		// Sort by date in descending order (Newest/Latest dates first)
		if (patientAppointmentHistoryList != null) {
		    patientAppointmentHistoryList.sort((a1, a2) -> a2.getAppointmentDate().compareTo(a1.getAppointmentDate()));
		}

		
		model.addAttribute("patientAppointmentHistoryList", patientAppointmentHistoryList);
		
		//for family member
		if (patient != null && patient.getFamilyMembers() != null) {
			model.addAttribute("familyMembersList", patient.getFamilyMembers());
		} else {
			model.addAttribute("familyMembersList", new java.util.ArrayList<FamilyMemberModel>());
		}
		
		return "/patient/dashboard";

	}
	
	@GetMapping("/book-appointment")
	public String checkoutAppointmentBill(@RequestParam(name = "doctorId") Integer doctorId, Model model, Principal principal) {
	  
	    // 1. Fetch patient
	    String usernameMob = principal.getName();
	    PatientModel patient = patientServices.getUserByContact(usernameMob);
	    model.addAttribute("patient", patient);
				
	    // =======================================================
	    // ADDED: Fetch and bind family members for booking choice
	    // =======================================================
	    if (patient != null && patient.getFamilyMembers() != null) {
	        model.addAttribute("familyMembersList", patient.getFamilyMembers());
	    } else {
	        model.addAttribute("familyMembersList", new java.util.ArrayList<>());
	    }

	    // 2. Query doctor details
	    Optional<DoctorModel> option = doctorRepository.findByDoctorId(doctorId);
	    DoctorModel doctor = option.get();

	    // 3. Calculate financial checkout parameters
	    double consultationFee = 500.00;
	    double taxAmount = consultationFee * 0.18;
	    double grandTotalAmount = consultationFee + taxAmount;

	    // 4. Bind parameters
	    model.addAttribute("doctor", doctor);
	    model.addAttribute("consultationFee", String.format("%.2f", consultationFee));
	    model.addAttribute("taxAmount", String.format("%.2f", taxAmount));
	    model.addAttribute("grandTotal", String.format("%.2f", grandTotalAmount));

	    return "/patient/billPage"; 
	}

	
	@GetMapping("/prescription/view")
	public String viewPrescriptionDetailsDataSummary(@RequestParam(name = "appointmentId") Integer appointmentId, 
	                                                 Model model) {

		PrescriptionModel prescription = prescriptionServices.findByAppointmentId(appointmentId);

	    // 2. Bind the data context records directly into Thymeleaf template variable mappings
	    model.addAttribute("prescription", prescription);
	    
	    // Renders the dedicated, read-only dashboard summary layout template file
	    return "/patient/prescriptionView"; 
	}

	@PostMapping("/family/add")
	public String addFamilyMember(
	        @ModelAttribute FamilyMemberModel familyMember,
	        @RequestParam("primaryPatientId") Integer primaryPatientId, // Captures ID from the hidden frontend field
	        RedirectAttributes redirectAttributes) {
		
		 try {
		        // 1. Fetch the primary patient entity context directly using your PatientRepository
		        PatientModel primaryPatient = patientServices.getPatientById(primaryPatientId);

		        // 2. Apply your Fallback Rule: If contact input is empty, copy the primary patient's number
		        if (familyMember.getContact() == null || familyMember.getContact().trim().isEmpty()) {
		            familyMember.setContact(primaryPatient.getContactNumber());
		        }

		        // 3. Link the family member profile to the primary patient profile object
		        familyMember.setPatient(primaryPatient);

		        // 4. Save directly to the database using the JpaRepository method
		        familyMemberRepository.save(familyMember);
		        
		        redirectAttributes.addFlashAttribute("successMessage", "Family member added successfully!");
		        
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Failed to add family member: " + e.getMessage());
		    }
		
		return "redirect:/patient/dashboard";
	}


}
