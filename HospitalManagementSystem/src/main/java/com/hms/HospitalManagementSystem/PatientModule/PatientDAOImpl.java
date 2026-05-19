package com.hms.HospitalManagementSystem.PatientModule;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class PatientDAOImpl implements PatientDAO {
	
	private final PatientRepository patientRepository;
	
	

	public PatientDAOImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}


	@Override
	public List<PatientModel> getAllPatient() {

		List<PatientModel> patientsList = patientRepository.findAll();
		
		if(patientsList == null || patientsList.isEmpty())
		{
			System.out.println("Pateint List is Empty");
			return null;
		}
		return patientsList;
	}


	@Override
	public PatientModel getUserByContact(String contact) {

		Optional<PatientModel> patientuser = patientRepository.findByContactNumber(contact);
		
		if(patientuser.get() == null)
		{
			return null;
		}
		
		PatientModel patient = patientuser.get();
		
		return patient;
	}

}
