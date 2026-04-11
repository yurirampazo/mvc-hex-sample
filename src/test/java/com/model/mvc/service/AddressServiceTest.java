package com.model.mvc.service;

import com.model.mvc.client.ViaCepClient;
import com.model.mvc.client.ViaCepResponseDTO;
import com.model.mvc.mapper.AddressMapper;
import com.model.mvc.model.Address;
import com.model.mvc.model.dto.AddressResponseDTO;
import com.model.mvc.model.dto.CreateAddressRequestDTO;
import com.model.mvc.model.dto.UpdateAddressRequestDTO;
import com.model.mvc.repository.AddressRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AddressService Tests")
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private CreateAddressRequestDTO createAddressRequestDTO;
    private UpdateAddressRequestDTO updateAddressRequestDTO;
    private AddressResponseDTO addressResponseDTO;
    private ViaCepResponseDTO viaCepResponseDTO;

    @BeforeEach
    void setUp() {
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
        createAddressRequestDTO.setNeighbourhood("Centro");
        createAddressRequestDTO.setCity("São Paulo");
        createAddressRequestDTO.setState("SP");
        createAddressRequestDTO.setNumber("123");

        updateAddressRequestDTO = new UpdateAddressRequestDTO();
        updateAddressRequestDTO.setId(1);
        updateAddressRequestDTO.setZipCode("01310-100");
        updateAddressRequestDTO.setStreetName("Avenida Paulista");
        updateAddressRequestDTO.setNeighbourhood("Centro");
        updateAddressRequestDTO.setCity("São Paulo");
        updateAddressRequestDTO.setState("SP");
        updateAddressRequestDTO.setNumber("123");

        addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(1);
        addressResponseDTO.setZipCode("01310-100");
        addressResponseDTO.setStreetName("Avenida Paulista");
        addressResponseDTO.setNeighbourhood("Centro");
        addressResponseDTO.setCity("São Paulo");
        addressResponseDTO.setState("SP");
        addressResponseDTO.setNumber(123);

        viaCepResponseDTO = new ViaCepResponseDTO(
            "01310-100",
            "Avenida Paulista",
            "",
            "",
            "Centro",
            "São Paulo",
            "SP",
            "São Paulo",
            "Sudeste",
            "3550308",
            "",
            "11",
            "7107"
        );
    }

    @Test
    @DisplayName("Should create address successfully")
    void testCreateAddressSuccess() {
        when(addressMapper.fromCreateDto(createAddressRequestDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.create(createAddressRequestDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("01310-100", result.getZipCode());
        assertEquals("Avenida Paulista", result.getStreetName());
        verify(addressMapper, times(1)).fromCreateDto(createAddressRequestDTO);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should update address successfully")
    void testUpdateAddressSuccess() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.update(updateAddressRequestDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(addressRepository, times(1)).findById(1);
        verify(addressMapper, times(1)).updateFromDto(updateAddressRequestDTO, address);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent address")
    void testUpdateAddressNotFound() {
        updateAddressRequestDTO.setId(99);
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.update(updateAddressRequestDTO));
        verify(addressRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Should get address entity by id successfully")
    void testGetEntityByIdSuccess() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        Address result = addressService.getEntityById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("01310-100", result.getZipCode());
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent address by id")
    void testGetEntityByIdNotFound() {
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.getEntityById(99));
        verify(addressRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Should find address by zip code successfully")
    void testFindByZipCodeSuccess() {
        String zipCode = "01310-100";
        when(viaCepClient.getViaCepAddress(zipCode)).thenReturn(viaCepResponseDTO);
        when(addressMapper.toAddressDto(viaCepResponseDTO)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressService.findByZipCode(zipCode);

        assertNotNull(result);
        assertEquals("01310-100", result.getZipCode());
        assertEquals("Avenida Paulista", result.getStreetName());
        verify(viaCepClient, times(1)).getViaCepAddress(zipCode);
        verify(addressMapper, times(1)).toAddressDto(viaCepResponseDTO);
    }

    @Test
    @DisplayName("Should throw exception when zip code is blank")
    void testFindByZipCodeBlank() {
        assertThrows(IllegalArgumentException.class, () -> addressService.findByZipCode(""));
        verify(viaCepClient, never()).getViaCepAddress(anyString());
    }

    @Test
    @DisplayName("Should throw exception when zip code is null")
    void testFindByZipCodeNull() {
        assertThrows(NullPointerException.class, () -> addressService.findByZipCode(null));
    }

    @Test
    @DisplayName("Should throw exception when zip code contains only spaces")
    void testFindByZipCodeOnlySpaces() {
        assertThrows(IllegalArgumentException.class, () -> addressService.findByZipCode("   "));
        verify(viaCepClient, never()).getViaCepAddress(anyString());
    }

    @Test
    @DisplayName("Should handle zip code with hyphens")
    void testFindByZipCodeWithHyphens() {
        String zipCode = "01310-100";
        when(viaCepClient.getViaCepAddress(zipCode)).thenReturn(viaCepResponseDTO);
        when(addressMapper.toAddressDto(viaCepResponseDTO)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressService.findByZipCode(zipCode);

        assertNotNull(result);
        verify(viaCepClient, times(1)).getViaCepAddress(zipCode);
    }

    @Test
    @DisplayName("Should handle zip code without hyphens")
    void testFindByZipCodeWithoutHyphens() {
        String zipCode = "01310100";
        when(viaCepClient.getViaCepAddress(zipCode)).thenReturn(viaCepResponseDTO);
        when(addressMapper.toAddressDto(viaCepResponseDTO)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressService.findByZipCode(zipCode);

        assertNotNull(result);
        verify(viaCepClient, times(1)).getViaCepAddress(zipCode);
    }
}

