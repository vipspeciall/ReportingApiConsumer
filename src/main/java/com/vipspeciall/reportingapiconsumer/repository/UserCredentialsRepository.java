package com.vipspeciall.reportingapiconsumer.repository;

import com.vipspeciall.reportingapiconsumer.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUniqueID(String uniqueID);
}
