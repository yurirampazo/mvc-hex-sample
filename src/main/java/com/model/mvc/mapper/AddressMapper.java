package com.model.mvc.mapper;

import com.model.mvc.model.Address;
import com.model.mvc.model.dto.AddressResponseDTO;
import com.model.mvc.model.dto.CreateAddressRequestDTO;
import com.model.mvc.model.dto.UpdateAddressRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  AddressResponseDTO toAddressResponseDto(Address address);

  Address toAddressCreatingResource(CreateAddressRequestDTO createAddressRequestDTO);

  Address toAddressUpdatingResource(UpdateAddressRequestDTO updateAddressRequestDTO);
}
