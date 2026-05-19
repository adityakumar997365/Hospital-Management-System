package com.hms.HospitalManagementSystem.AdminModule;

import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public class AdminDAOImpl implements AdminDAO {
	
	private final AdminRepository adminRepository;
	
	
	public AdminDAOImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public AdminModel findtheUser(String username) {

		AdminModel user = adminRepository.findByName(username);
		
		return user;
	}

	@Override
	public AdminModel getUserByContact(String contact) {

		Optional<AdminModel> adminuser = adminRepository.findByContactNumber(contact);
		
		if(adminuser.get() == null)
		{
			return null;
		}
		
		AdminModel user = adminuser.get();
		
		return user;
	}

	
	
	

}
