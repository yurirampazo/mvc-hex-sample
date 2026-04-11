package com.model.hex.infrastructure.persistence.adapter;

import com.model.hex.domain.model.Customer;
import com.model.hex.domain.ports.out.CustomerRepositoryPort;
import com.model.hex.infrastructure.persistence.entity.CustomerEntity;
import com.model.hex.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import com.model.hex.infrastructure.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

  private final CustomerJpaRepository jpaRepository;
  private final CustomerPersistenceMapper mapper;

  @Override
  public Optional<Customer> findById(Integer id) {
    return jpaRepository.findByIdWithAddresses(id)
        .map(mapper::toDomainGetById);
  }

  @Override
  public List<Customer> findAll() {
    return jpaRepository.findAll()
        .stream()
        .map(mapper::toDomainGetAll)
        .toList();
  }

  @Override
  public Customer save(Customer customer) {
    CustomerEntity entity = mapper.toEntity(customer);
    CustomerEntity saved = jpaRepository.save(entity);
    return mapper.toDomainGetById(saved);
  }

  @Override
  public void deleteById(Integer id) {
    jpaRepository.deleteById(id);
  }
}