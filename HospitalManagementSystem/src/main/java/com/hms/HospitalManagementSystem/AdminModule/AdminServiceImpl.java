package com.hms.HospitalManagementSystem.AdminModule;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminServices{
	
	private final AdminDAO admindao;
	

	public AdminServiceImpl(AdminDAO admindao) {
		this.admindao = admindao;
	}

	@Override
	public AdminModel findtheUser(String username) {

		return admindao.findtheUser(username);
	}

	@Override
	public AdminModel getUserByContact(String contact) {

		
		return admindao.getUserByContact(contact);
	}


}
