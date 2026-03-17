package com.model.mvc.infrastructure.web.mapper;

import com.model.mvc.domain.model.Address;
import com.model.mvc.infrastructure.web.dto.ViaCepResponseDTO;
import com.model.mvc.infrastructure.web.dto.CreateAddressRequestDTO;
import com.model.mvc.infrastructure.web.dto.UpdateAddressRequestDTO;
import com.model.mvc.infrastructure.web.dto.AddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  AddressResponseDTO toResponse(Address address);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customers", ignore = true)
  Address fromCreateDto(CreateAddressRequestDTO dto);

  @Mapping(target = "customers", ignore = true)
  void updateFromDto(UpdateAddressRequestDTO dto, @MappingTarget Address address);

  @Mapping(target = "zipCode", source = "zipcode")
  @Mapping(target = "streetName", source = "street")
  @Mapping(target = "neighbourhood", source = "neighborhood")
  @Mapping(target = "city", source = "city")
  @Mapping(target = "state", source = "stateName")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "countryCode", ignore = true)
  AddressResponseDTO toAddressDto(ViaCepResponseDTO viaCep);
}
