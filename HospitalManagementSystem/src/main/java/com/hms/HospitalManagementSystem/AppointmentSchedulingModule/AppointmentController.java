package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServices appointmentServices;

    @PostMapping("/confirm/{id}")
    public String confirmAppointment(@PathVariable("id") Integer id) {
        appointmentServices.updateStatus(id, AppointmentModel.Status.CONFIRMED, null);
        return "redirect:/admin/dashboard"; // Change this path to match your dashboard page endpoint
    }

//    @PostMapping("/cancel/{id}")
//    public String cancelAppointment(@PathVariable("id") Integer id) {
//        appointmentServices.updateStatus(id, AppointmentModel.Status.CANCELLED);
//        return "redirect:/admin/dashboard"; // Change this path to match your dashboard page endpoint
//    }
    
    @PostMapping("/cancel/{id}")
    public String cancelAppointment(
            @PathVariable("id") Integer id, 
            @RequestParam("cancellationReason") String reason, // <-- Captures input from popup box
            RedirectAttributes redirectAttributes) {
        
        // Pass parameters to service layer for logging or sending email alerts
        appointmentServices.updateStatus(id, AppointmentModel.Status.CANCELLED, reason);
        
        // Append the dynamic reason directly to your flash attribute string message
        redirectAttributes.addFlashAttribute("AppointmentRejectMessage", 
            "Appointment request with ID: #" + id + " has been cancelled. Reason: " + reason);
        
        return "redirect:/admin/dashboard";
    }


}
