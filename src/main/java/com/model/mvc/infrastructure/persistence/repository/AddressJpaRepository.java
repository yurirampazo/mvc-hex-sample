package com.model.mvc.infrastructure.persistence.repository;

import com.model.mvc.infrastructure.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Integer> {
}
