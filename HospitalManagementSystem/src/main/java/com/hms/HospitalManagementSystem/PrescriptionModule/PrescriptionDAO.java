package com.hms.HospitalManagementSystem.PrescriptionModule;

import java.util.List;

public interface PrescriptionDAO {

	List<PrescriptionModel> getPatientHistory(Integer id);
	
	List<PrescriptionModel> getPrescriptionOfDoctor(Integer id);
	
	void completeAppointmentWithPrescription(PrescriptionModel prescription);
	
	PrescriptionModel findByAppointmentId(Integer appointmentId);
}
