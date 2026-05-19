package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServices appointmentServices;

    @PostMapping("/confirm/{id}")
    public String confirmAppointment(@PathVariable("id") Integer id) {
        appointmentServices.updateStatus(id, AppointmentModel.Status.CONFIRMED);
        return "redirect:/admin/dashboard"; // Change this path to match your dashboard page endpoint
    }

    @PostMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Integer id) {
        appointmentServices.updateStatus(id, AppointmentModel.Status.CANCELLED);
        return "redirect:/admin/dashboard"; // Change this path to match your dashboard page endpoint
    }
}
