package com.hms.HospitalManagementSystem.DoctorModule;

import jakarta.persistence.*;

@Entity
@Table(name = "Doctor")
public class DoctorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorId")
    private Integer doctorId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "contactNumber", length = 15)
    private String contactNumber;

    @Lob
    @Column(name = "availabilitySchedule", columnDefinition = "TEXT")
    private String availabilitySchedule;

    public DoctorModel() {}

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

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
