package com.model.mvc.mapper;

import com.model.mvc.model.Address;
import com.model.mvc.model.dto.AddressResponseDTO;
import com.model.mvc.model.dto.CreateAddressRequestDTO;
import com.model.mvc.model.dto.UpdateAddressRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  AddressResponseDTO toAddressResponseDto(Address address);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customers", ignore = true)
  Address toAddressCreatingResource(CreateAddressRequestDTO createAddressRequestDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customers", ignore = true)
  Address toAddressUpdatingResource(UpdateAddressRequestDTO updateAddressRequestDTO);
}
