package com.hms.HospitalManagementSystem.UserModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

	Optional<UserModel> findByUsername(String username);

}
