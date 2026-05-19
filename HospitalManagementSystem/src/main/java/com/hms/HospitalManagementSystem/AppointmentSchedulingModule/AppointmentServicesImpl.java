package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel.Status;


@Service
public class AppointmentServicesImpl implements AppointmentServices {
	
	private final AppointmentDAO appointmentDAO;
	
	public AppointmentServicesImpl(AppointmentDAO appointmentDAO) {
		this.appointmentDAO = appointmentDAO;
	}



	@Override
	public List<AppointmentModel> getAllAppointments() {
	
		return appointmentDAO.getAllAppointments();
	}

	@Override
	public List<AppointmentModel> getConfirmedAppointments(Integer doctorId) {

		return appointmentDAO.getConfirmedAppointments(doctorId);
	}



	@Override
	public List<AppointmentModel> getAllReviewedAppointments() {

		return appointmentDAO.getAllReviewedAppointments();
	}



	@Override
	public void updateStatus(Integer id, Status confirmed) {
	      appointmentDAO.updateStatus(id, confirmed);
	}



	@Override
	public AppointmentModel getAppointmentById(Integer id) {

		return appointmentDAO.getAppointmentById(id);
	}

}
