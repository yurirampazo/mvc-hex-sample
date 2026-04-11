package com.model.hex.domain.ports.out;

import com.model.hex.domain.model.Address;

import java.util.Optional;

public interface AddressRepositoryPort {
  Optional<Address> findById(Integer id);

  Address save(Address address);
}