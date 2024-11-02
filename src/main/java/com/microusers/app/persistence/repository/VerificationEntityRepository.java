package com.microusers.app.persistence.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microusers.app.persistence.entity.UserVerification;

@Repository
public interface VerificationEntityRepository extends JpaRepository<UserVerification, Integer>{

    

}
