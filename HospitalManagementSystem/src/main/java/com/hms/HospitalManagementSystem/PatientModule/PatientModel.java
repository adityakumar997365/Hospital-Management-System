package com.hms.HospitalManagementSystem.PatientModule;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberModel;

@Entity
@Table(name = "Patient")
public class PatientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientId")
    private Integer patientId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "contactNumber", length = 15)
    private String contactNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Lob
    @Column(name = "medicalHistory", columnDefinition = "TEXT")
    private String medicalHistory;
    
    // Add this inside PatientModel class for easy data retrieval
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<FamilyMemberModel> familyMembers;

    


    public PatientModel() {}

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory ) {
        this.medicalHistory = medicalHistory;
    }
    
 // Getter and Setter for familyMembers
    public java.util.List<FamilyMemberModel> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(java.util.List<FamilyMemberModel> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
