package com.hms.HospitalManagementSystem.UserModule;

public class UserDTO {

    private Integer userId;
    private String username;
    private String password;
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String role;

    public UserDTO() {}

    public UserDTO(Integer userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

