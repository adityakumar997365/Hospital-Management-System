package com.hms.HospitalManagementSystem.PatientRegisterModule;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PatientRegister")
public class PatientRegisterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientRegisterId")
    private Integer patientRegisterId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "contactNumber", length = 10)
    private String contactNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "registrationStatus", nullable = false)
    private RegistrationStatus registrationStatus = RegistrationStatus.PENDING;

    // Added nullable reason column (nullable = true is the default JPA behavior)
    @Column(name = "reason", length = 255, nullable = true)
    private String reason;

    public enum RegistrationStatus {
        APPROVED, PENDING, REJECTED
    }

    public PatientRegisterModel() {}

    public Integer getPatientRegisterId() {
        return patientRegisterId;
    }

    public void setPatientRegisterId(Integer patientRegisterId) {
        this.patientRegisterId = patientRegisterId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    // Getter and Setter for reason
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
