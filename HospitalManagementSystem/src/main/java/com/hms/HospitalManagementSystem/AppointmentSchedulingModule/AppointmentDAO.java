package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel.Status;

public interface AppointmentDAO {

	List<AppointmentModel> getAllAppointments();

	public List<AppointmentModel> getConfirmedAppointments(Integer doctorId);

	public List<AppointmentModel> getAllReviewedAppointments();

	void updateStatus(Integer id, Status status, String reason);

	public AppointmentModel getAppointmentById(Integer id);
	
	public List<AppointmentModel> getCompletedAppointmentsByPatientId(Integer patientId);

	// for patient appointment history tab
	public List<AppointmentModel> getAllAppointmentsByPatientId(Integer patientId);

}
