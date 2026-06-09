package com.hms.HospitalManagementSystem.PatientModule;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PatinetServicesImpl implements PatientServices {

	private final PatientDAO patientDAO;
	
	
	public PatinetServicesImpl(PatientDAO patientDAO) {
		this.patientDAO = patientDAO;
	}


	@Override
	public List<PatientModel> getAllPatient() {
		return patientDAO.getAllPatient();
	}


	@Override
	public PatientModel getUserByContact(String contact) {

		return patientDAO.getUserByContact(contact);
	}


	@Override
	public PatientModel getPatientById(Integer patinetid) {

		return patientDAO.getPatientById(patinetid);
	}

}
