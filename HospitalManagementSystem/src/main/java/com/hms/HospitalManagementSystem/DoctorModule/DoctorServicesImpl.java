package com.hms.HospitalManagementSystem.DoctorModule;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DoctorServicesImpl implements DoctorServices {
	
	private final DoctorDAO doctorDao;
	
	
	public DoctorServicesImpl(DoctorDAO doctorDao) {
		super();
		this.doctorDao = doctorDao;
	}


	@Override
	public List<DoctorModel> getAllDoctor() {

		return doctorDao.getAllDoctor();
	}


	@Override
	public DoctorModel addNewDoctor(DoctorDTO dto) {
		
		return doctorDao.addNewDoctor(dto);
	}


	@Override
	public DoctorModel getUserByContact(String usernameMob) {
		
		return doctorDao.getUserByContact(usernameMob);
	}

}
