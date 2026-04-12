package com.model.hex.application.service;

import com.model.hex.domain.model.Address;
import com.model.hex.domain.ports.out.AddressClientPort;
import com.model.hex.domain.ports.out.AddressRepositoryPort;
import com.model.hex.infrastructure.web.dto.AddressResponseDTO;
import com.model.hex.infrastructure.web.dto.CreateAddressRequestDTO;
import com.model.hex.infrastructure.web.dto.UpdateAddressRequestDTO;
import com.model.hex.infrastructure.web.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AddressService Unit Tests - Hexagonal Architecture")
class AddressServiceTest {

    @Mock
    private AddressRepositoryPort repository;

    @Mock
    private AddressMapper mapper;

    @Mock
    private AddressClientPort addressClient;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private CreateAddressRequestDTO createAddressRequestDTO;
    private UpdateAddressRequestDTO updateAddressRequestDTO;
    private AddressResponseDTO addressResponseDTO;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setId(1);
        address.setZipCode("01310-100");
        address.setStreetName("Avenida Paulista");
        address.setNeighbourhood("Centro");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setCustomers(new HashSet<>());

        createAddressRequestDTO = new CreateAddressRequestDTO();
        createAddressRequestDTO.setZipCode("01310-100");

        updateAddressRequestDTO = new UpdateAddressRequestDTO();
        updateAddressRequestDTO.setId(1);
        updateAddressRequestDTO.setZipCode("01310-100");

        addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(1);
        addressResponseDTO.setZipCode("01310-100");
    }

    @Test
    @DisplayName("Should create address successfully")
    void testCreateSuccess() {
        when(mapper.fromCreateDto(createAddressRequestDTO)).thenReturn(address);
        when(repository.save(address)).thenReturn(address);

        Address result = addressService.create(createAddressRequestDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should update address successfully")
    void testUpdateSuccess() {
        when(repository.findById(1)).thenReturn(Optional.of(address));
        when(repository.save(address)).thenReturn(address);

        Address result = addressService.update(updateAddressRequestDTO);

        assertNotNull(result);
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent address")
    void testUpdateNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        updateAddressRequestDTO.setId(99);

        assertThrows(RuntimeException.class, () -> addressService.update(updateAddressRequestDTO));
    }

    @Test
    @DisplayName("Should get address by ID successfully")
    void testGetEntityByIdSuccess() {
        when(repository.findById(1)).thenReturn(Optional.of(address));

        Address result = addressService.getEntityById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent address")
    void testGetEntityByIdNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.getEntityById(99));
    }

    @Test
    @DisplayName("Should find address by zip code successfully")
    void testFindByZipCodeSuccess() {
        when(addressClient.findByZipCode("01310-100")).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressService.findByZipCode("01310-100");

        assertNotNull(result);
        assertEquals("01310-100", result.getZipCode());
        verify(addressClient, times(1)).findByZipCode("01310-100");
    }

    @Test
    @DisplayName("Should throw exception for blank zip code")
    void testFindByZipCodeBlank() {
        assertThrows(IllegalArgumentException.class, () -> addressService.findByZipCode(""));
        verify(addressClient, never()).findByZipCode(any());
    }

    @Test
    @DisplayName("Should throw exception for null zip code")
    void testFindByZipCodeNull() {
        assertThrows(NullPointerException.class, () -> addressService.findByZipCode(null));
    }
}

