package com.model.hex.infrastructure.persistence.adapter;

import com.model.hex.domain.model.Customer;
import com.model.hex.infrastructure.persistence.entity.CustomerEntity;
import com.model.hex.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import com.model.hex.infrastructure.persistence.repository.CustomerJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerPersistenceAdapterTest {

    @Mock
    private CustomerJpaRepository jpaRepository;

    @Mock
    private CustomerPersistenceMapper mapper;

    @InjectMocks
    private CustomerPersistenceAdapter adapter;

    private Customer customer;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        customer = new Customer();       // use your builder/constructor if available
        customerEntity = new CustomerEntity();
    }

    @Test
    @DisplayName("findById – returns mapped customer when entity exists")
    void findById_existingId_returnsMappedCustomer() {
        when(jpaRepository.findByIdWithAddresses(1)).thenReturn(Optional.of(customerEntity));
        when(mapper.toDomainGetById(customerEntity)).thenReturn(customer);

        Optional<Customer> result = adapter.findById(1);

        assertThat(result).isPresent().contains(customer);
        verify(jpaRepository).findByIdWithAddresses(1);
        verify(mapper).toDomainGetById(customerEntity);
    }

    @Test
    @DisplayName("findById – returns empty Optional when entity does not exist")
    void findById_nonExistingId_returnsEmptyOptional() {
        when(jpaRepository.findByIdWithAddresses(99)).thenReturn(Optional.empty());

        Optional<Customer> result = adapter.findById(99);

        assertThat(result).isEmpty();
        verify(jpaRepository).findByIdWithAddresses(99);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("findAll – maps and returns all customers")
    void findAll_withEntities_returnsMappedList() {
        CustomerEntity entity2 = new CustomerEntity();
        Customer customer2 = new Customer();

        when(jpaRepository.findAll()).thenReturn(List.of(customerEntity, entity2));
        when(mapper.toDomainGetAll(customerEntity)).thenReturn(customer);
        when(mapper.toDomainGetAll(entity2)).thenReturn(customer2);

        List<Customer> result = adapter.findAll();

        assertThat(result).hasSize(2).containsExactly(customer, customer2);
        verify(jpaRepository).findAll();
        verify(mapper, times(2)).toDomainGetAll(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("findAll – returns empty list when no entities exist")
    void findAll_noEntities_returnsEmptyList() {
        when(jpaRepository.findAll()).thenReturn(List.of());

        List<Customer> result = adapter.findAll();

        assertThat(result).isEmpty();
        verify(jpaRepository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("save – persists entity and returns mapped domain object")
    void save_validCustomer_returnsSavedCustomer() {
        CustomerEntity savedEntity = new CustomerEntity();
        Customer savedCustomer = new Customer();

        when(mapper.toEntity(customer)).thenReturn(customerEntity);
        when(jpaRepository.save(customerEntity)).thenReturn(savedEntity);
        when(mapper.toDomainGetById(savedEntity)).thenReturn(savedCustomer);

        Customer result = adapter.save(customer);

        assertThat(result).isEqualTo(savedCustomer);
        verify(mapper).toEntity(customer);
        verify(jpaRepository).save(customerEntity);
        verify(mapper).toDomainGetById(savedEntity);
    }

    @Test
    @DisplayName("deleteById – delegates delete to JPA repository")
    void deleteById_existingId_callsRepository() {
        doNothing().when(jpaRepository).deleteById(1);

        adapter.deleteById(1);

        verify(jpaRepository).deleteById(1);
        verifyNoInteractions(mapper);
    }
}
