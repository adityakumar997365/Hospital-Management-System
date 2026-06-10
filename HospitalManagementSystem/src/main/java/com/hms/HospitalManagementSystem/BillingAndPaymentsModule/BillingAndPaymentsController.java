package com.hms.HospitalManagementSystem.BillingAndPaymentsModule;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentRepository;
import com.hms.HospitalManagementSystem.BillingAndPaymentsModule.BillModel.PaymentStatus;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorRepository;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberModel;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberRepository;
import com.hms.HospitalManagementSystem.PatientModule.PatientModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientRepository;

import jakarta.transaction.Transactional;

@Controller
public class BillingAndPaymentsController {
	
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final BillRepository billRepository;
	private final AppointmentRepository appointmentRepository;
	private final FamilyMemberRepository familyMemberRepository;

	public BillingAndPaymentsController(DoctorRepository doctorRepository, PatientRepository patientRepository,
			BillRepository billRepository, AppointmentRepository appointmentRepository,
			FamilyMemberRepository familyMemberRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.billRepository = billRepository;
		this.appointmentRepository = appointmentRepository;
		this.familyMemberRepository = familyMemberRepository;
	}


	@Transactional
	@PostMapping("/patient/book-appointment/process-payment")
	public String processPatientBillingTransaction(@RequestParam(name = "doctorId") Integer doctorId,
	                                               @RequestParam(name = "patientId") Integer patientId,
	                                               @RequestParam(name = "familyMemberId", required = false) Integer familyMemberId, // <-- ADDED PARAMETER
	                                               @RequestParam(name = "amount") String billAmount,
	                                               @RequestParam(name = "paymentStatus") String paymentStatus,
	                                               @RequestParam(name = "appointmentDate") String appointmentDate,
	                                               @RequestParam(name = "timeSlot") String timeSlot,
	                                               RedirectAttributes redirectAttributes) {
	    try {
	    // Debug logging verification lines
	    System.out.println("========== PROCESSING HMS APPOINTMENT INVOICE ==========");
	    System.out.println("Target Doctor ID Primary Key: " + doctorId);
	    System.out.println("Active Patient ID Primary Key: " + patientId);
	    System.out.println("Total Amount Charged: " + billAmount);
	    System.out.println("User Selected Payment Status Choice: " + paymentStatus);
	    System.out.println("======================================================");

	    
	    // Saving Bill Information
	    BillModel bill = new BillModel();
	    
	    DoctorModel doctorRef = doctorRepository.getReferenceById(doctorId);
	    bill.setDoctor(doctorRef);
	    
	    PatientModel patientRef = patientRepository.getReferenceById(patientId);
	    bill.setPatient(patientRef);
	    
	    FamilyMemberModel familyMemberRef = null;
	    if (familyMemberId != null) {
	        familyMemberRef = familyMemberRepository.getReferenceById(familyMemberId);
	    }
	    bill.setFamilyMember(familyMemberRef);
        
	    BigDecimal finalAmount = new BigDecimal(billAmount);
	    bill.setTotalAmount(finalAmount);
	    
	    if(paymentStatus.equals("PAID"))
	    	bill.setPaymentStatus(PaymentStatus.PAID);
	    if(paymentStatus.equals("UNPAID"))
	    	bill.setPaymentStatus(PaymentStatus.UNPAID);
	    
	    System.out.println(bill.getPaymentStatus());
	    
	    billRepository.save(bill);
	    
	    // Saving ppointment Details 
	    
	    AppointmentModel appointment = new AppointmentModel();
	    
	    LocalDate date = LocalDate.parse(appointmentDate); // Format must be YYYY-MM-DD

	    appointment.setAppointmentDate(date);
	    
	    appointment.setDoctor(doctorRef);
	    appointment.setPatient(patientRef);
	    appointment.setFamilyMember(familyMemberRef);
	    appointment.setTimeSlot(timeSlot);
	    appointment.setBill(bill);
	    appointment.setStatus(AppointmentModel.Status.REVIEWING);
	    
	    
	    appointmentRepository.save(appointment);
	    
	    redirectAttributes.addFlashAttribute("billSuccessMessage", 
	            "Appointment scheduled successfully for " + 
	            (familyMemberRef != null ? familyMemberRef.getName() : patientRef.getName()) + 
	            "! Payment Status: " + paymentStatus);
	        
	    } catch (Exception e) {
	        System.err.println("Fatal database mapping transaction tracking error occurred: " + e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "Failed to book appointment: " + e.getMessage());
	        return "redirect:/patient/book-appointment";
	    }

	    // Redirect the patient back to their portal review terminal console frame view
	    return "redirect:/patient/dashboard"; 
	}


}
