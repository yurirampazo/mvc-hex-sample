package com.model.hex.application.service;

import com.model.hex.domain.model.Address;
import com.model.hex.domain.model.Customer;
import com.model.hex.domain.ports.out.CustomerRepositoryPort;
import com.model.hex.infrastructure.web.dto.*;
import com.model.hex.infrastructure.web.mapper.AddressMapper;
import com.model.hex.infrastructure.web.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Unit Tests")
class CustomerServiceTest {

    // ── Use AddressService (concrete type), matching the constructor signature ──
    @Mock
    private CustomerRepositoryPort repository;

    @Mock
    private AddressService addressUseCase;   // ← concrete class, not the interface

    @Mock
    private CustomerMapper mapper;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;
    private CreateCustomerRequestDTO createRequestNoAddresses;
    private CreateCustomerRequestDTO createRequestWithAddress;
    private UpdateCustomerRequestDTO updateRequestNoAddresses;
    private UpdateCustomerRequestDTO updateRequestWithAddress;
    private Address address;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1);
        customer.setName("João Silva");
        customer.setEmail("joao@example.com");
        customer.setBirthDate(LocalDate.of(1990, 5, 15));
        customer.setAddresses(new HashSet<>());

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(1);
        customerResponseDTO.setName("João Silva");
        customerResponseDTO.setEmail("joao@example.com");
        customerResponseDTO.setBirthDate(LocalDate.of(1990, 5, 15));

        createRequestNoAddresses = new CreateCustomerRequestDTO();
        createRequestNoAddresses.setName("João Silva");
        createRequestNoAddresses.setEmail("joao@example.com");
        createRequestNoAddresses.setBirthDate(LocalDate.of(1990, 5, 15));
        createRequestNoAddresses.setAddresses(new HashSet<>());

        CreateAddressRequestDTO createAddrDto = new CreateAddressRequestDTO();
        createAddrDto.setZipCode("01310-100");

        createRequestWithAddress = new CreateCustomerRequestDTO();
        createRequestWithAddress.setName("João Silva");
        createRequestWithAddress.setEmail("joao@example.com");
        createRequestWithAddress.setBirthDate(LocalDate.of(1990, 5, 15));
        createRequestWithAddress.setAddresses(new HashSet<>(Set.of(createAddrDto)));

        updateRequestNoAddresses = new UpdateCustomerRequestDTO();
        updateRequestNoAddresses.setName("João Silva Updated");
        updateRequestNoAddresses.setEmail("joao.updated@example.com");
        updateRequestNoAddresses.setBirthDate(LocalDate.of(1990, 5, 15));
        updateRequestNoAddresses.setAddresses(null);

        UpdateAddressRequestDTO updateAddrDto = new UpdateAddressRequestDTO();

        updateRequestWithAddress = new UpdateCustomerRequestDTO();
        updateRequestWithAddress.setName("João Silva Updated");
        updateRequestWithAddress.setEmail("joao.updated@example.com");
        updateRequestWithAddress.setBirthDate(LocalDate.of(1990, 5, 15));
        updateRequestWithAddress.setAddresses(new HashSet<>(Set.of(updateAddrDto)));

        address = new Address();
        address.setId(1);
        address.setZipCode("01310-100");
        address.setStreetName("Avenida Paulista");
        address.setCustomers(new HashSet<>());
    }

    @Test
    @DisplayName("getById – returns mapped DTO when customer exists")
    void getById_existingId_returnsMappedDTO() {
        when(repository.findById(1)).thenReturn(Optional.of(customer));
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.getById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("João Silva");
        verify(repository).findById(1);
        verify(mapper).toResponse(customer);
    }

    @Test
    @DisplayName("getById – throws RuntimeException when customer not found")
    void getById_nonExistingId_throwsRuntimeException() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getById(99))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Customer not found");

        verify(repository).findById(99);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("getAll – maps and returns all customers")
    void getAll_withCustomers_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(customer));
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        List<CustomerResponseDTO> result = customerService.getAll();

        assertThat(result).hasSize(1).containsExactly(customerResponseDTO);
        verify(repository).findAll();
        verify(mapper).toResponse(customer);
    }

    @Test
    @DisplayName("getAll – returns empty list when no customers exist")
    void getAll_noCustomers_returnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<CustomerResponseDTO> result = customerService.getAll();

        assertThat(result).isEmpty();
        verify(repository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("create – saves customer with no addresses")
    void create_noAddresses_savesAndReturnsMappedDTO() {
        when(mapper.fromCreateDto(createRequestNoAddresses)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.create(createRequestNoAddresses);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("João Silva");
        verify(mapper).fromCreateDto(createRequestNoAddresses);
        verify(repository).save(customer);
        verify(mapper).toResponse(customer);
        verifyNoInteractions(addressMapper);
    }

    @Test
    @DisplayName("create – links address to customer and saves")
    void create_withOneAddress_linksAddressAndSaves() {
        customer.setAddresses(new HashSet<>());
        address.setCustomers(new HashSet<>());

        when(mapper.fromCreateDto(createRequestWithAddress)).thenReturn(customer);
        when(addressMapper.fromCreateDto(any(CreateAddressRequestDTO.class))).thenReturn(address);
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.create(createRequestWithAddress);

        assertThat(result).isNotNull();
        assertThat(customer.getAddresses()).contains(address);
        assertThat(address.getCustomers()).contains(customer);
        verify(addressMapper).fromCreateDto(any(CreateAddressRequestDTO.class));
        verify(repository).save(customer);
    }

    @Test
    @DisplayName("update – updates fields only when addresses is null")
    void update_nullAddresses_updatesFieldsWithoutTouchingAddresses() {
        when(repository.findById(1)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.update(1, updateRequestNoAddresses);

        assertThat(result).isNotNull();
        verify(repository).findById(1);
        verify(mapper).updateFromDto(updateRequestNoAddresses, customer); // must be called
        verify(repository).save(customer);
        verifyNoInteractions(addressUseCase);
    }

    @Test
    @DisplayName("update – replaces addresses when address set is provided")
    void update_withAddresses_replacesAddressesAndSaves() {
        Address existingAddress = new Address();
        existingAddress.setCustomers(new HashSet<>(Set.of(customer)));
        customer.setAddresses(new HashSet<>(Set.of(existingAddress)));
        address.setCustomers(new HashSet<>());

        when(repository.findById(1)).thenReturn(Optional.of(customer));
        when(addressUseCase.update(any(UpdateAddressRequestDTO.class))).thenReturn(address);
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.update(1, updateRequestWithAddress);

        assertThat(result).isNotNull();
        assertThat(existingAddress.getCustomers()).doesNotContain(customer);
        assertThat(customer.getAddresses()).contains(address);
        assertThat(address.getCustomers()).contains(customer);
        verify(addressUseCase).update(any(UpdateAddressRequestDTO.class));
        verify(repository).save(customer);
    }

    @Test
    @DisplayName("update – throws RuntimeException when customer not found")
    void update_nonExistingId_throwsRuntimeException() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.update(99, updateRequestNoAddresses))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Customer not found");

        verify(repository).findById(99);
        verifyNoInteractions(mapper, addressUseCase);
    }

    @Test
    @DisplayName("delete – delegates to repository and completes without error")
    void delete_existingId_callsRepository() {
        doNothing().when(repository).deleteById(1);

        customerService.delete(1);

        verify(repository).deleteById(1);
        verifyNoInteractions(mapper, addressUseCase, addressMapper);
    }
}