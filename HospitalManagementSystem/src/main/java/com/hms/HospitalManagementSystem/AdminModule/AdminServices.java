package com.hms.HospitalManagementSystem.AdminModule;

public interface AdminServices {

	AdminModel findtheUser(String username);
	
	AdminModel getUserByContact(String contact);
}
