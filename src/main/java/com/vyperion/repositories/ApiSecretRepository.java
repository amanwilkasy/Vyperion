package com.vyperion.repositories;

import com.vyperion.dto.ApiSecret;
import com.vyperion.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSecretRepository extends JpaRepository<ApiSecret, Integer> {
    ApiSecret findApiSecretByUserId(User userId);
}