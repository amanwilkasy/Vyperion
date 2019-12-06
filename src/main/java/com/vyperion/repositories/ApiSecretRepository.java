package com.vyperion.repositories;

import com.vyperion.dto.ApiSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSecretRepository extends JpaRepository<ApiSecret, Integer> {
    ApiSecret findApiSecretByUserId(String id);
}