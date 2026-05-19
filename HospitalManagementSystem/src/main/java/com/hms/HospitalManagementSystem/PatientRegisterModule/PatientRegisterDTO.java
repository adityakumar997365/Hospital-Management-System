package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.time.LocalDate;

public class PatientRegisterDTO {

    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;
    private String password;

    public PatientRegisterDTO() {}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
