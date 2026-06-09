package com.hms.HospitalManagementSystem.AdminModule;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentServices;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorDTO;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorServices;
import com.hms.HospitalManagementSystem.PatientModule.PatientModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientServices;
import com.hms.HospitalManagementSystem.PatientRegisterModule.PatientRegisterModel;
import com.hms.HospitalManagementSystem.PatientRegisterModule.PatientRegisterServices;
import com.hms.HospitalManagementSystem.UserModule.UserModel;
import com.hms.HospitalManagementSystem.UserModule.UserModel.Role;
import com.hms.HospitalManagementSystem.UserModule.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminServices adminservices;
	private final PatientRegisterServices patientRegisterServices;
	private final DoctorServices doctorservices;
	private final PatientServices patientServices;
	private final AppointmentServices appointmentServices;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	
	

	public AdminController(AdminServices adminservices, PatientRegisterServices patientRegisterServices,
			DoctorServices doctorservices, PatientServices patientServices, AppointmentServices appointmentServices,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.adminservices = adminservices;
		this.patientRegisterServices = patientRegisterServices;
		this.doctorservices = doctorservices;
		this.patientServices = patientServices;
		this.appointmentServices = appointmentServices;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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
	public String addNewDoctor(@ModelAttribute DoctorDTO doctorDto,
	                           @RequestParam(name = "password") String rawPassword, 
	                           RedirectAttributes redirectAttributes) {
	    
	    // 1. Persist the Doctor details using your service layer workflow
	    DoctorModel doctorModel = doctorservices.addNewDoctor(doctorDto);
	    
	    // 2. Build and persist the associated User Login credentials
	    UserModel doctorUser = new UserModel();
	    doctorUser.setUsername(doctorDto.getContactNumber());
	    
	    String securePasswordHash = passwordEncoder.encode(rawPassword);		
	    doctorUser.setPassword(securePasswordHash); // BCrypt utilizing Spring Security
	    
	    doctorUser.setRole(Role.DOCTOR);
	 
	    UserModel userModel = userRepository.save(doctorUser);
	    
	    Integer generatedId = doctorModel.getDoctorId(); 
	    
	    // 3. Construct a clear message string containing ID, username, and password credentials
	    String formalSuccessText = "Doctor onboarded successfully with ID: " + generatedId + 
	                               ". [Username: " + userModel.getUsername() + 
	                               " | Password: " + rawPassword + "]";
	    
	    // 4. Inject the compiled message safely into the redirect flash attributes map
	    redirectAttributes.addFlashAttribute("successMessage", formalSuccessText);
	    
	    return "redirect:/admin/dashboard";
	}



	
	@PostMapping("/patient-registration/approve/{id}")
	public String approvePatient(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	     
	    patientRegisterServices.approveRegistration(id);
	    
	    // Pass the success message safely through the redirect flash session tunnel
	    redirectAttributes.addFlashAttribute("patientSuccessMessage", "Patient registration with ID: " + id + " has been approved successfully.");
	    
	    return "redirect:/admin/dashboard"; 
	}


	@PostMapping("/patient-registration/reject/{id}")
	public String rejectPatient(
	        @PathVariable("id") Integer id, 
	        @RequestParam("rejectionReason") String reason, // <-- Captures the value from the popup box
	        RedirectAttributes redirectAttributes) {
	    
	    // Pass both the ID and the reason to your service layer
	    patientRegisterServices.rejectRegistration(id, reason);
	    
	    // Inject a distinct rejection tracking message key with the reason included
	    redirectAttributes.addFlashAttribute("patientRejectMessage", 
	        "Patient registration with ID: " + id + " has been rejected. Reason: " + reason);
	    
	    return "redirect:/admin/dashboard";
	}

	
	//for patient record view in Admin page
	@GetMapping("patient/completed-appointments/{id}")
	public String viewPatientHistoryByAdmin(@PathVariable("id") Integer id, Model model) {
	    
	    // 1. Query appointments filtered by Patient ID and the "COMPLETED" status token
	    List<AppointmentModel> completedRecords = appointmentServices.getCompletedAppointmentsByPatientId(id);
	    
	    // count the records
	  //  int totalAppointmentsCount = completedRecords.size();
	    
	    // 2. Fetch Patient descriptive metadata to show their identity in the layout headers
	    PatientModel patientProfile = patientServices.getPatientById(id);
	    
	    
	    // 3. Inject attributes into your UI render map container
	    model.addAttribute("appointments", completedRecords);
	    model.addAttribute("patient", patientProfile);
	  //  model.addAttribute("countRecord", totalAppointmentsCount);
	    
	    // 4. Returns template file: src/main/resources/templates/admin/patient-history.html
	    return "admin/patient-history"; 
	}


}
