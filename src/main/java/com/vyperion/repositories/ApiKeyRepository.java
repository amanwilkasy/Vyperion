package com.vyperion.repositories;

import com.vyperion.dto.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

    List<ApiKey> findAllByUserId(String id);

    ApiKey findApiKeyByUserIdAndKeyName(String id, String keyName);
}
