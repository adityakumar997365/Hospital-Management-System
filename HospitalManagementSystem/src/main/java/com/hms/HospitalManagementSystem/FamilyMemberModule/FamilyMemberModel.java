package com.hms.HospitalManagementSystem.FamilyMemberModule;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.hms.HospitalManagementSystem.PatientModule.PatientModel;

@Entity
@Table(name = "family_members")
public class FamilyMemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "relationship", length = 50, nullable = false)
    private String relationship;

    @Column(name = "gender", length = 10, nullable = false) // Added gender field
    private String gender;

    @Column(name = "contact", length = 15) // Nullable because it is optional
    private String contact;

    // Establishing connection to the Patient table using PatientModel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientModel patient;

    // Default No-Arg Constructor
    public FamilyMemberModel() {}

    // Getters and Setters
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGender() { // Getter for gender
        return gender;
    }

    public void setGender(String gender) { // Setter for gender
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }
}
