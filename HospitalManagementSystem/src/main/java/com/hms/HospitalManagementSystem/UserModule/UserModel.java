package com.hms.HospitalManagementSystem.UserModule;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public enum Role {
        ADMIN, PATIENT, DOCTOR
    }

    public UserModel() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
