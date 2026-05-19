package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel.Status;


public interface AppointmentServices {
	
	List<AppointmentModel> getAllAppointments();
   
	public List<AppointmentModel> getConfirmedAppointments(Integer doctorId);
	
	public List<AppointmentModel> getAllReviewedAppointments();

	void updateStatus(Integer id, Status confirmed);

	public AppointmentModel getAppointmentById(Integer id);

}
