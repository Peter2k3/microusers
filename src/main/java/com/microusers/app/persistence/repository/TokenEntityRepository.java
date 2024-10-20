package com.microusers.app.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microusers.app.persistence.entity.TokenEntity;

@Repository
public interface TokenEntityRepository extends JpaRepository<TokenEntity, Integer>{

    public Optional<TokenEntity> findByToken(String token);

}
