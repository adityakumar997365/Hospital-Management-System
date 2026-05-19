package com.hms.HospitalManagementSystem.PrescriptionModule;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PrescriptionServicesImpl implements PrescriptionServices {
	
	private final PrescriptionDAO prescriptionDAO;
	
	

	public PrescriptionServicesImpl(PrescriptionDAO prescriptionDAO) {
		this.prescriptionDAO = prescriptionDAO;
	}



	@Override
	public List<PrescriptionModel> getPatientHistory(Integer patientid) {
		
		return prescriptionDAO.getPatientHistory(patientid);
	}



	@Override
	public List<PrescriptionModel> getPrescriptionOfDoctor(Integer doctorid) {

		return prescriptionDAO.getPrescriptionOfDoctor(doctorid);
	}



	@Override
	public void completeAppointmentWithPrescription(PrescriptionModel prescription) {

		prescriptionDAO.completeAppointmentWithPrescription(prescription);
	}



	@Override
	public PrescriptionModel findByAppointmentId(Integer appointmentId) {

		return prescriptionDAO.findByAppointmentId(appointmentId);
	}

}
