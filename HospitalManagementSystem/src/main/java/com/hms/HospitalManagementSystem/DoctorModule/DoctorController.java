package com.hms.HospitalManagementSystem.DoctorModule;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentServices;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionModel;
import com.hms.HospitalManagementSystem.PrescriptionModule.PrescriptionServices;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	private final DoctorServices doctorservices;
	private final AppointmentServices appointmentServices;
	private final PrescriptionServices prescriptionServices;

	public DoctorController(DoctorServices doctorservices, AppointmentServices appointmentServices,
			PrescriptionServices prescriptionServices) {
		this.doctorservices = doctorservices;
		this.appointmentServices = appointmentServices;
		this.prescriptionServices = prescriptionServices;
	}

	@GetMapping("/dashboard")
	public String showDoctorDashboard(Model model, Principal principal) {

		// for Doctor
		String usernameMob = principal.getName();

		DoctorModel doctor = doctorservices.getUserByContact(usernameMob);

		model.addAttribute("doctorName", doctor.getName());

		model.addAttribute("doctorDetails", doctor);

		// for Appointment List of Doctor
		List<AppointmentModel> appointmentList = appointmentServices.getConfirmedAppointments(doctor.getDoctorId());

		model.addAttribute("appointmentsList", appointmentList);

		// for Doctor Prescription History
		List<PrescriptionModel> pateintPrescribeList = prescriptionServices.getPrescriptionOfDoctor(doctor.getDoctorId());

		model.addAttribute("pateintPrescribeList", pateintPrescribeList);

		return "/doctor/dashboard";

	}
	
	 @GetMapping("/appointment/{id}/prescribe")
	    public String showPrescriptionForm(@PathVariable("id") Integer id, Model model) {
	        AppointmentModel appointment = appointmentServices.getAppointmentById(id);
	        model.addAttribute("appointment", appointment);
	        return "doctor/prescribe"; // Loads templates/doctor/prescribe.html
	    }

	    // 2. Capture parameters, process state updates, and redirect to safe root view
	    @PostMapping("/appointment/{id}/prescribe")
	    public String processPrescription(@PathVariable("id") Integer id,
	                                      @RequestParam("diagnosis") String diagnosis,
	                                      @RequestParam("medicine") String medicine,
	                                      @RequestParam("dosage") String dosage) {
	    	
	    	AppointmentModel appointment = appointmentServices.getAppointmentById(id);
	    	
	    	PrescriptionModel prescription = new PrescriptionModel();
	    	
	    	prescription.setMedicine(medicine);
	    	prescription.setDosage(dosage);
	    	prescription.setDiagnose(diagnosis);
	    	
	    	prescription.setDoctor(appointment.getDoctor());
	    	
	    	prescription.setPatient(appointment.getPatient());
	    	
	    	prescription.setAppointment(appointment);
	    	
	    	prescription.setCreatedDate(LocalDate.now());
	    	
	        
	        // Executes multi-table service update changes cleanly inside an transactional context
	        prescriptionServices.completeAppointmentWithPrescription(prescription);
	        
	        appointmentServices.updateStatus(id, AppointmentModel.Status.COMPLETED);
	        
	        return "redirect:/doctor/dashboard";
	    }

}
