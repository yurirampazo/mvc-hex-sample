package com.model.mvc.domain.ports.out;

import com.model.mvc.domain.model.Address;

import java.util.Optional;

public interface AddressRepositoryPort {
  Optional<Address> findById(Integer id);

  Address save(Address address);
}