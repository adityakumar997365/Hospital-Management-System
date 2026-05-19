package com.hms.HospitalManagementSystem.AdminModule;

import java.time.LocalDate;

public class AdminDTO {

    private Integer adminId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;

    public AdminDTO() {}

    public AdminDTO(Integer adminId, String name, LocalDate dateOfBirth, String gender, String contactNumber, String address) {
        this.adminId = adminId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
