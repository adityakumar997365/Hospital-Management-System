package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hms.HospitalManagementSystem.PatientModule.PatientModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientRepository;
import com.hms.HospitalManagementSystem.UserModule.UserModel;
import com.hms.HospitalManagementSystem.UserModule.UserRepository;

import jakarta.transaction.Transactional;

@Repository
public class PatientRegisterDAOImpl implements PatientRegisterDAO {

	private final PatientRegisterRepository patientRegisterRepository;
	private final PasswordEncoder passwordEncoder;
	private final PatientRepository patientRepository;
	private final UserRepository userRepository;

	// Spring automatically injects the configured BCrypt bean
	public PatientRegisterDAOImpl(PatientRegisterRepository patientRegisterRepository, PasswordEncoder passwordEncoder,
			PatientRepository patientRepository, UserRepository userRepository) {
		this.patientRegisterRepository = patientRegisterRepository;
		this.passwordEncoder = passwordEncoder;
		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
	}

	// Saving the register Pateint Details in database
	@Override
	@Transactional
	public PatientRegisterModel registerNewPatient(PatientRegisterDTO dto) {

		PatientRegisterModel entity = new PatientRegisterModel();

		entity.setName(dto.getName());
		entity.setDateOfBirth(dto.getDateOfBirth());
		entity.setGender(dto.getGender());
		entity.setContactNumber(dto.getContactNumber());
		entity.setAddress(dto.getAddress());

		String securePasswordHash = passwordEncoder.encode(dto.getPassword());
		entity.setPassword(securePasswordHash);

		return patientRegisterRepository.save(entity);
	}

	@Override
	public List<PatientRegisterModel> findAllRegisterPatient() {

		List<PatientRegisterModel> listofRegisterPatient = patientRegisterRepository.findAll();

		List<PatientRegisterModel> listofPendingRegisterPatient = new ArrayList<PatientRegisterModel>();

		if (listofRegisterPatient == null || listofRegisterPatient.isEmpty()) {

			return null;
		}

		for (PatientRegisterModel registerPatient : listofRegisterPatient) {
			if (registerPatient.getRegistrationStatus().name() == "PENDING") {

				listofPendingRegisterPatient.add(registerPatient);
			}
		}

		return listofPendingRegisterPatient;

	}

	@Override
	public PatientRegisterModel checkStatusOfApproval(String contactNumber) {

		Optional<PatientRegisterModel> patient = patientRegisterRepository.findByContactNumber(contactNumber);

		if (!patient.isPresent()) {
			System.out.println("No Data Exist..!");
			return null; // Safely returns null to your controller without a crash
		}
		return patient.get();
	}

	@Override
	@Transactional
	public void approveRegistration(Integer registerId) {

		PatientRegisterModel regData = patientRegisterRepository.findById(registerId)
				.orElseThrow(() -> new IllegalArgumentException("Registration record not found: " + registerId));

		// 1. Prevent duplicate copies if already approved
		if (regData.getRegistrationStatus() == PatientRegisterModel.RegistrationStatus.APPROVED) {
			return;
		}

		// 2. Transfer matching values into the persistent operational Patient table
		PatientModel activePatient = new PatientModel();
		activePatient.setName(regData.getName());
		activePatient.setDateOfBirth(regData.getDateOfBirth());
		activePatient.setGender(regData.getGender());
		activePatient.setContactNumber(regData.getContactNumber());
		activePatient.setAddress(regData.getAddress());

		patientRepository.save(activePatient);

		// 3. Create User as PATIENT in User Table
		UserModel newPatientUser = new UserModel();
		newPatientUser.setRole(UserModel.Role.PATIENT);
		newPatientUser.setUsername(regData.getContactNumber());
		newPatientUser.setPassword(regData.getPassword()); // Keeps the existing hash pass
		
		userRepository.save(newPatientUser);

		// 4. Update the request status
		regData.setRegistrationStatus(PatientRegisterModel.RegistrationStatus.APPROVED);
		patientRegisterRepository.save(regData);
	}
	
	 @Override
	 @Transactional
	    public void rejectRegistration(Integer registerId, String reason) {
	        PatientRegisterModel regData = patientRegisterRepository.findById(registerId)
	                .orElseThrow(() -> new IllegalArgumentException("Registration record not found: " + registerId));

	        regData.setRegistrationStatus(PatientRegisterModel.RegistrationStatus.REJECTED);
	        regData.setReason(reason);
	        patientRegisterRepository.save(regData);
	    }
}
