package com.model.mvc.infrastructure.persistence.repository;

import com.model.mvc.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {

  @Query("SELECT c FROM CustomerEntity c LEFT JOIN FETCH c.addresses WHERE c.id = :id")
  Optional<CustomerEntity> findByIdWithAddresses(@Param("id") Integer id);
}