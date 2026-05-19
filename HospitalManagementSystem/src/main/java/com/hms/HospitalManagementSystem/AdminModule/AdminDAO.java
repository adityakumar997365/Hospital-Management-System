package com.hms.HospitalManagementSystem.AdminModule;

public interface AdminDAO {
	
	AdminModel findtheUser(String username);
	
	AdminModel getUserByContact(String contact);

}
