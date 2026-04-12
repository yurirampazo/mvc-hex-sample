package com.model.hex.infrastructure.persistence.adapter;

import com.model.hex.domain.model.Address;
import com.model.hex.infrastructure.persistence.entity.AddressEntity;
import com.model.hex.infrastructure.persistence.mapper.AddressPersistenceMapper;
import com.model.hex.infrastructure.persistence.repository.AddressJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AddressPersistenceAdapter Unit Tests")
class AddressPersistenceAdapterTest {

    @Mock
    private AddressJpaRepository jpaRepository;

    @Mock
    private AddressPersistenceMapper mapper;

    @InjectMocks
    private AddressPersistenceAdapter adapter;

    private Address address;
    private AddressEntity addressEntity;

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

        addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setZipCode("01310-100");
        addressEntity.setStreetName("Avenida Paulista");
    }

    @Test
    @DisplayName("Should find address by ID")
    void testFindById() {
        when(jpaRepository.findById(1)).thenReturn(Optional.of(addressEntity));
        when(mapper.toDomain(addressEntity)).thenReturn(address);

        Optional<Address> result = adapter.findById(1);

        assertTrue(result.isPresent());
        assertEquals("01310-100", result.get().getZipCode());
        verify(jpaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should return empty when address not found")
    void testFindByIdNotFound() {
        when(jpaRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Address> result = adapter.findById(99);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should save address")
    void testSave() {
        when(mapper.toEntity(address)).thenReturn(addressEntity);
        when(jpaRepository.save(addressEntity)).thenReturn(addressEntity);
        when(mapper.toDomain(addressEntity)).thenReturn(address);

        Address result = adapter.save(address);

        assertNotNull(result);
        assertEquals("01310-100", result.getZipCode());
        verify(jpaRepository, times(1)).save(addressEntity);
    }
}

