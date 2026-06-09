package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel.Status;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {

	private final AppointmentRepository appointmentRepository;

	public AppointmentDAOImpl(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public List<AppointmentModel> getAllAppointments() {

		List<AppointmentModel> appointmentList = appointmentRepository.findAll();

		if (appointmentList == null || appointmentList.isEmpty()) {
			return null;
		}

		return appointmentList;
	}

	@Override
	public List<AppointmentModel> getConfirmedAppointments(Integer doctorId) {

		return appointmentRepository.findByDoctor_DoctorIdAndStatus(doctorId, AppointmentModel.Status.CONFIRMED);
	}

	@Override
	public List<AppointmentModel> getAllReviewedAppointments() {

		return appointmentRepository.findByStatus(AppointmentModel.Status.REVIEWING);
	}

	@Override
	public void updateStatus(Integer id, Status status, String reason) {
		AppointmentModel appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));

		appointment.setStatus(status);
		appointment.setReason(reason);
		appointmentRepository.save(appointment);
	}

	@Override
	public AppointmentModel getAppointmentById(Integer id) {
		
			return appointmentRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Appointment records not found: " + id));
	}

	@Override
	public List<AppointmentModel> getCompletedAppointmentsByPatientId(Integer patientId) {

		return appointmentRepository.findByPatientPatientIdAndStatus(patientId, AppointmentModel.Status.COMPLETED);
		
	}

	@Override
	public List<AppointmentModel> getAllAppointmentsByPatientId(Integer patientId) {

		return appointmentRepository.findByPatientPatientId(patientId);
	}

	

}
