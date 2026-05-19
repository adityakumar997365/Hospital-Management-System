package com.hms.HospitalManagementSystem.AdminModule;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentServices;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorDTO;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorServices;
import com.hms.HospitalManagementSystem.PatientModule.PatientModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientServices;
import com.hms.HospitalManagementSystem.PatientRegisterModule.PatientRegisterModel;
import com.hms.HospitalManagementSystem.PatientRegisterModule.PatientRegisterServices;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminServices adminservices;
	private final PatientRegisterServices patientRegisterServices;
	private final DoctorServices doctorservices;
	private final PatientServices patientServices;
	private final AppointmentServices appointmentServices;
	
	public AdminController(AdminServices adminservices,
			PatientRegisterServices patientRegisterServices,
			DoctorServices doctorservices,
			PatientServices patientServices,
			AppointmentServices appointmentServices) {
        this.adminservices = adminservices;
        this.patientRegisterServices = patientRegisterServices;
        this.doctorservices = doctorservices;
        this.patientServices = patientServices;
        this.appointmentServices = appointmentServices;
	}


	@GetMapping("/dashboard")
	public String showAdminDashboard(Model model, Principal principal) {
		
		//for Admin
		String usernameMob = principal.getName();
        
        AdminModel admin = adminservices.getUserByContact(usernameMob);
        
        model.addAttribute("adminName",  admin.getName() );
        
		model.addAttribute("adminDetails", admin);
		
		//for PatientRegister Request List
		List<PatientRegisterModel> patientRegisterList = patientRegisterServices.findAllRegisterPatient();
		
		model.addAttribute("registerPateintList", patientRegisterList);
		
		//for Existing Doctor List
		List<DoctorModel> doctorsList = doctorservices.getAllDoctor();
		
		model.addAttribute("doctorsList",doctorsList);
		
		//for Existing Patient List
		
		List<PatientModel> patientsList = patientServices.getAllPatient();
		
		model.addAttribute("patientsList",patientsList);
		
		//for Appointment request
		
		List<AppointmentModel> appointmentList = appointmentServices.getAllReviewedAppointments();

		model.addAttribute("appointmentsList", appointmentList);
		
		return "admin/dashboard"; // Resolves to templates/admindashboard.html
	}
	
	@PostMapping("/add-doctor")
	public String addNewDoctor(@ModelAttribute DoctorDTO dto, Model model) {
		
		DoctorModel doctorModel = doctorservices.addNewDoctor(dto);
		
		Integer generatedId = doctorModel.getDoctorId(); 
		
		// 3. Inject the success text string into the Thymeleaf Model layer
		model.addAttribute("successMessage", "Doctor added successfully with ID: {} " + generatedId);
		
		return "redirect:/admin/dashboard";
		
	}
	
	 @PostMapping("/patient-registration/approve/{id}")
	    public String approvePatient(@PathVariable("id") Integer id) {
		 
	        patientRegisterServices.approveRegistration(id);
	        return "redirect:/admin/dashboard"; // Redirect back to your pending list dashboard page
	    }

	    @PostMapping("/patient-registration/reject/{id}")
	    public String rejectPatient(@PathVariable("id") Integer id) {
	    	
	        patientRegisterServices.rejectRegistration(id);
	        return "redirect:/admin/dashboard";
	    }

}
