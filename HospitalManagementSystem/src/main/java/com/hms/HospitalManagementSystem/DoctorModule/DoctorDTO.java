package com.hms.HospitalManagementSystem.DoctorModule;

public class DoctorDTO {

    private String name;
    private String specialization;
    private String contactNumber;
    private String availabilitySchedule;

    // Default Constructor
    public DoctorDTO() {}

    // Parameterized Constructor for quick instantiation
    public DoctorDTO(String name, String specialization, String contactNumber, String availabilitySchedule) {
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.availabilitySchedule = availabilitySchedule;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAvailabilitySchedule() {
        return availabilitySchedule;
    }

    public void setAvailabilitySchedule(String availabilitySchedule) {
        this.availabilitySchedule = availabilitySchedule;
    }
}
