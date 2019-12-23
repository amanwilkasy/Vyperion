package com.vyperion.repositories;

import com.vyperion.dto.ApiKey;
import com.vyperion.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    List<ApiKey> findAllByUserId(User userId);
}
