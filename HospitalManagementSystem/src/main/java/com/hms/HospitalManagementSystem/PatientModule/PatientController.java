package com.hms.HospitalManagementSystem.PatientModule;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorRepository;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorServices;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionModel;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionServices;


@Controller
@RequestMapping("/patient")
public class PatientController {

	private final PatientServices patientServices;
	private final DoctorServices doctorservices;
	private final DoctorRepository doctorRepository;
	private final PrescriptionServices prescriptionServices;
	

	

	public PatientController(PatientServices patientServices, DoctorServices doctorservices,
			DoctorRepository doctorRepository, PrescriptionServices prescriptionServices) {

		this.patientServices = patientServices;
		this.doctorservices = doctorservices;
		this.doctorRepository = doctorRepository;
		this.prescriptionServices = prescriptionServices;
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
		
		List<PrescriptionModel> pateintAppointmentList = prescriptionServices.getPatientHistory(patient.getPatientId());
		
		model.addAttribute("pateintAppointmentList", pateintAppointmentList);
		
		return "/patient/dashboard";

	}
	
	@GetMapping("/book-appointment")
	public String checkoutAppointmentBill(@RequestParam(name = "doctorId") Integer doctorId, Model model, Principal principal) {
	  
		// 1. for patient
				String usernameMob = principal.getName();

				PatientModel patient = patientServices.getUserByContact(usernameMob);

				model.addAttribute("patient", patient);
				
	    // 2. Query the specialist details out of your DAO layer
	    // Note: Ensure your DAO has a lookup method by primary key ID matching this call
	    Optional<DoctorModel> option = doctorRepository.findByDoctorId(doctorId);
	    
	    DoctorModel doctor = option.get();

	    // 3. Calculate financial checkout parameters
	    double consultationFee = 500.00; // Hardcoded default clinic service charge
	    double taxAmount = consultationFee * 0.18; // 18% standard healthcare service cess
	    double grandTotalAmount = consultationFee + taxAmount;

	    // 4. Bind parameters directly into Thymeleaf model variables
	    model.addAttribute("doctor", doctor);
	    model.addAttribute("consultationFee", String.format("%.2f", consultationFee));
	    model.addAttribute("taxAmount", String.format("%.2f", taxAmount));
	    model.addAttribute("grandTotal", String.format("%.2f", grandTotalAmount));

	    return "/patient/billPage"; // Renders the brand new central checkout bill template view page
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



}
