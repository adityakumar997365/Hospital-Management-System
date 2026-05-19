package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<AppointmentModel, Integer> {

	 List<AppointmentModel> findByDoctor_DoctorIdAndStatus(Integer doctorId, AppointmentModel.Status status);

	 List<AppointmentModel> findByStatus(AppointmentModel.Status status);

}
