package com.model.hex.infrastructure.persistence.repository;

import com.model.hex.infrastructure.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Integer> {
}
