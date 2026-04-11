package com.model.hex.infrastructure.persistence.adapter;

import com.model.hex.domain.model.Address;
import com.model.hex.domain.ports.out.AddressRepositoryPort;
import com.model.hex.infrastructure.persistence.entity.AddressEntity;
import com.model.hex.infrastructure.persistence.mapper.AddressPersistenceMapper;
import com.model.hex.infrastructure.persistence.repository.AddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressPersistenceAdapter implements AddressRepositoryPort {

  private final AddressJpaRepository jpaRepository;
  private final AddressPersistenceMapper mapper;

  @Override
  public Optional<Address> findById(Integer id) {
    return jpaRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public Address save(Address address) {
    AddressEntity entity = mapper.toEntity(address);
    AddressEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }
}
