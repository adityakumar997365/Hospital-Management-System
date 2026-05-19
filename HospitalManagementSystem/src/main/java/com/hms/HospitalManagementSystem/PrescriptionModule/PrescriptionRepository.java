package com.hms.HospitalManagementSystem.PrescriptionModule;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<PrescriptionModel, Integer> {
	
	
	List<PrescriptionModel> findByPatient_PatientId(Integer patientId);
	
	List<PrescriptionModel> findByDoctor_DoctorId(Integer doctorId);
	
	Optional<PrescriptionModel> findByAppointmentAppointmentId(Integer appointmentId);


}
