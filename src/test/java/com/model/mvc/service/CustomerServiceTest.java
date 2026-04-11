package com.model.mvc.service;

import com.model.mvc.mapper.CustomerMapper;
import com.model.mvc.model.Address;
import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.*;
import com.model.mvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;
    private CreateCustomerRequestDTO createCustomerRequestDTO;
    private UpdateCustomerRequestDTO updateCustomerRequestDTO;
    private Address address;
    private CreateAddressRequestDTO createAddressRequestDTO;
    private UpdateAddressRequestDTO updateAddressRequestDTO;

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
        customerResponseDTO.setAddresses(new HashSet<>());

        createCustomerRequestDTO = new CreateCustomerRequestDTO();
        createCustomerRequestDTO.setName("João Silva");
        createCustomerRequestDTO.setEmail("joao@example.com");
        createCustomerRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        createCustomerRequestDTO.setAddresses(new HashSet<>());

        updateCustomerRequestDTO = new UpdateCustomerRequestDTO();
        updateCustomerRequestDTO.setName("João Silva Updated");
        updateCustomerRequestDTO.setEmail("joao.updated@example.com");
        updateCustomerRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        updateCustomerRequestDTO.setAddresses(new HashSet<>());

        address = new Address();
        address.setId(1);
        address.setZipCode("01310-100");
        address.setStreetName("Avenida Paulista");
        address.setNeighbourhood("Centro");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setNumber("123");
        address.setCustomers(new HashSet<>());

        createAddressRequestDTO = new CreateAddressRequestDTO();
        createAddressRequestDTO.setZipCode("01310-100");
        createAddressRequestDTO.setStreetName("Avenida Paulista");

        updateAddressRequestDTO = new UpdateAddressRequestDTO();
        updateAddressRequestDTO.setId(1);
        updateAddressRequestDTO.setZipCode("01310-100");
    }

    @Test
    @DisplayName("Should get customer by id successfully")
    void testGetByIdSuccess() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("João Silva", result.getName());
        assertEquals("joao@example.com", result.getEmail());
        verify(customerRepository, times(1)).findById(1);
        verify(customerMapper, times(1)).toResponse(customer);
    }

    @Test
    @DisplayName("Should throw exception when customer not found by id")
    void testGetByIdNotFound() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getById(99));
        verify(customerRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Should get all customers successfully")
    void testGetAllSuccess() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.toResponseGetAll(customer)).thenReturn(customerResponseDTO);

        List<CustomerResponseDTO> result = customerService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João Silva", result.get(0).getName());
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).toResponseGetAll(customer);
    }

    @Test
    @DisplayName("Should get empty list when no customers exist")
    void testGetAllEmpty() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        List<CustomerResponseDTO> result = customerService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should create customer successfully without addresses")
    void testCreateCustomerSuccess() {
        createCustomerRequestDTO.setAddresses(new HashSet<>());
        when(customerMapper.fromCreateDto(createCustomerRequestDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.create(createCustomerRequestDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        verify(customerRepository, times(1)).save(customer);
        verify(customerMapper, times(1)).fromCreateDto(createCustomerRequestDTO);
    }

    @Test
    @DisplayName("Should create customer successfully with addresses")
    void testCreateCustomerWithAddresses() {
        createCustomerRequestDTO.setAddresses(Set.of(createAddressRequestDTO));
        when(customerMapper.fromCreateDto(createCustomerRequestDTO)).thenReturn(customer);
        when(addressService.create(any(CreateAddressRequestDTO.class))).thenReturn(address);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.create(createCustomerRequestDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        verify(addressService, times(1)).create(any(CreateAddressRequestDTO.class));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Should update customer successfully without addresses")
    void testUpdateCustomerSuccessNoAddresses() {
        updateCustomerRequestDTO.setAddresses(null);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.update(1, updateCustomerRequestDTO);

        assertNotNull(result);
        verify(customerRepository, times(1)).findById(1);
        verify(customerMapper, times(1)).updateFromDto(updateCustomerRequestDTO, customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Should update customer successfully with addresses")
    void testUpdateCustomerSuccessWithAddresses() {
        customer.setAddresses(new HashSet<>(List.of(address)));
        updateCustomerRequestDTO.setAddresses(Set.of(updateAddressRequestDTO));
        
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(addressService.update(any(UpdateAddressRequestDTO.class))).thenReturn(address);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        // Act
        CustomerResponseDTO result = customerService.update(1, updateCustomerRequestDTO);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findById(1);
        verify(addressService, times(1)).update(any(UpdateAddressRequestDTO.class));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent customer")
    void testUpdateCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerService.update(99, updateCustomerRequestDTO));
        verify(customerRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Should delete customer successfully")
    void testDeleteCustomerSuccess() {
        // Arrange
        doNothing().when(customerRepository).deleteById(1);

        // Act
        customerService.delete(1);

        // Assert
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Should handle multiple addresses in create")
    void testCreateCustomerWithMultipleAddresses() {
        // Arrange
        Address address2 = new Address();
        address2.setId(2);
        address2.setZipCode("01310-101");
        address2.setCustomers(new HashSet<>());

        CreateAddressRequestDTO createAddressRequestDTO2 = new CreateAddressRequestDTO();
        createAddressRequestDTO2.setZipCode("01310-101");

        createCustomerRequestDTO.setAddresses(Set.of(createAddressRequestDTO, createAddressRequestDTO2));
        
        when(customerMapper.fromCreateDto(createCustomerRequestDTO)).thenReturn(customer);
        when(addressService.create(any(CreateAddressRequestDTO.class)))
            .thenReturn(address)
            .thenReturn(address2);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponseDTO);

        // Act
        CustomerResponseDTO result = customerService.create(createCustomerRequestDTO);

        // Assert
        assertNotNull(result);
        verify(addressService, times(2)).create(any(CreateAddressRequestDTO.class));
    }
}

