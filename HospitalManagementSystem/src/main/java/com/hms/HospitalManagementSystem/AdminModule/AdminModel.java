package com.hms.HospitalManagementSystem.AdminModule;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Admin")
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminId")
    private Integer adminId;

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

    public AdminModel() {}

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
