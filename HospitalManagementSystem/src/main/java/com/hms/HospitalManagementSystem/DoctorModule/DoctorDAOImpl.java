package com.hms.HospitalManagementSystem.DoctorModule;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public class DoctorDAOImpl implements DoctorDAO {
	
	private final DoctorRepository doctorRepository;

	public DoctorDAOImpl(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	@Override
	public List<DoctorModel> getAllDoctor() {
		List<DoctorModel> allList = doctorRepository.findAll();
		
		if(allList == null || allList.isEmpty())
		{
			return null;
		}
		return allList;
	}

	@Override
	@Transactional
	public DoctorModel addNewDoctor(DoctorDTO dto) {

		DoctorModel entity = new DoctorModel();
		
		entity.setName(dto.getName()); 
		entity.setSpecialization(dto.getSpecialization());
		entity.setAvailabilitySchedule(dto.getAvailabilitySchedule());
		entity.setContactNumber(dto.getContactNumber());
		
		return doctorRepository.save(entity);
		
	}


	@Override
	public DoctorModel getUserByContact(String contact) {
		
		Optional<DoctorModel> doctoruser = doctorRepository.findByContactNumber(contact);
		
		if(doctoruser.get() == null)
		{
			return null;
		}
		
		DoctorModel user = doctoruser.get();
		
		return user;
	}
	
	

}
