package com.hms.HospitalManagementSystem.PrescriptionModule;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class PrescriptionDAOImpl implements PrescriptionDAO {
	
	private final PrescriptionRepository prescriptionRepository;
	
	

	public PrescriptionDAOImpl(PrescriptionRepository prescriptionRepository) {
		super();
		this.prescriptionRepository = prescriptionRepository;
	}



	@Override
	public List<PrescriptionModel> getPatientHistory(Integer id) {

		return prescriptionRepository.findByPatient_PatientId(id);
		 
	}



	@Override
	public List<PrescriptionModel> getPrescriptionOfDoctor(Integer id) {
		
		return prescriptionRepository.findByDoctor_DoctorId(id);
	}



	@Override
	public void completeAppointmentWithPrescription(PrescriptionModel prescription) {

		prescriptionRepository.save(prescription);
	}



	@Override
	public PrescriptionModel findByAppointmentId(Integer appointmentId) {

		Optional<PrescriptionModel> prescription = prescriptionRepository.findByAppointmentAppointmentId(appointmentId);
		
		return prescription.get();
	}

}
