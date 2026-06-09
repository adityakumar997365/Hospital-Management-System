package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<AppointmentModel, Integer> {

	 List<AppointmentModel> findByDoctor_DoctorIdAndStatus(Integer doctorId, AppointmentModel.Status status);

	 List<AppointmentModel> findByStatus(AppointmentModel.Status status);
	 
	 // for admin pannel so admin can see the patient medical history
	 List<AppointmentModel> findByPatientPatientIdAndStatus(Integer patientId, AppointmentModel.Status status);

	 //for patient histroy tab
	 List<AppointmentModel> findByPatientPatientId(Integer patientId);
}
