package com.model.hex.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.model.hex.domain.ports.in.CustomerUseCase;
import com.model.hex.infrastructure.web.dto.CreateCustomerRequestDTO;
import com.model.hex.infrastructure.web.dto.CustomerResponseDTO;
import com.model.hex.infrastructure.web.dto.UpdateCustomerRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@DisplayName("CustomerController — integration tests")
class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private CustomerUseCase customerUseCase;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CustomerResponseDTO customerResponseDTO;
    private CreateCustomerRequestDTO createCustomerRequestDTO;
    private UpdateCustomerRequestDTO updateCustomerRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(1);
        customerResponseDTO.setName("João Silva");
        customerResponseDTO.setEmail("joao@example.com");
        customerResponseDTO.setBirthDate(LocalDate.of(1990, 5, 15));

        createCustomerRequestDTO = new CreateCustomerRequestDTO();
        createCustomerRequestDTO.setName("João Silva");
        createCustomerRequestDTO.setEmail("joao@example.com");
        createCustomerRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        createCustomerRequestDTO.setAddresses(new java.util.HashSet<>());

        updateCustomerRequestDTO = new UpdateCustomerRequestDTO();
        updateCustomerRequestDTO.setName("João Updated");
        updateCustomerRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
    }

    @Nested
    @DisplayName("GET /customer/{id}")
    class GetById {

        @Test
        @DisplayName("returns 200 and customer body for existing id")
        void existingId_returns200() throws Exception {
            when(customerUseCase.getById(1)).thenReturn(customerResponseDTO);

            mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@example.com"));

            verify(customerUseCase).getById(1);
        }

        @Test
        @DisplayName("delegates to use case with correct id")
        void existingId_delegatesToUseCase() throws Exception {
            when(customerUseCase.getById(2)).thenReturn(customerResponseDTO);

            mockMvc.perform(get("/customer/2"))
                .andExpect(status().isOk());

            verify(customerUseCase).getById(2);
            verify(customerUseCase, never()).getById(1);
        }
    }

    @Nested
    @DisplayName("GET /customer")
    class GetAll {

        @Test
        @DisplayName("returns 200 and list of customers")
        void returns200AndList() throws Exception {
            when(customerUseCase.getAll()).thenReturn(List.of(customerResponseDTO));

            mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("João Silva"));

            verify(customerUseCase).getAll();
        }

        @Test
        @DisplayName("returns 200 and empty list when no customers exist")
        void returns200AndEmptyList() throws Exception {
            when(customerUseCase.getAll()).thenReturn(List.of());

            mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

            verify(customerUseCase).getAll();
        }
    }

    @Nested
    @DisplayName("POST /customer")
    class Create {

        @Test
        @DisplayName("returns 201 and created customer body")
        void validRequest_returns201() throws Exception {
            when(customerUseCase.create(any())).thenReturn(customerResponseDTO);

            mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createCustomerRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("João Silva"));

            verify(customerUseCase).create(any());
        }

        @Test
        @DisplayName("delegates to use case exactly once")
        void validRequest_delegatesOnce() throws Exception {
            when(customerUseCase.create(any())).thenReturn(customerResponseDTO);

            mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createCustomerRequestDTO)))
                .andExpect(status().isCreated());

            verify(customerUseCase, times(1)).create(any());
        }
    }

    @Nested
    @DisplayName("PUT /customer/{id}")
    class Update {

        @Test
        @DisplayName("returns 204 No Content on successful update")
        void validRequest_returns204() throws Exception {
            when(customerUseCase.update(eq(1), any())).thenReturn(customerResponseDTO);

            mockMvc.perform(put("/customer/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateCustomerRequestDTO)))
                .andExpect(status().isNoContent());

            verify(customerUseCase).update(eq(1), any());
        }

        @Test
        @DisplayName("delegates to use case with correct id")
        void validRequest_delegatesWithCorrectId() throws Exception {
            when(customerUseCase.update(eq(1), any())).thenReturn(customerResponseDTO);

            mockMvc.perform(put("/customer/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateCustomerRequestDTO)))
                .andExpect(status().isNoContent());

            verify(customerUseCase).update(eq(1), any());
            verify(customerUseCase, never()).update(eq(99), any());
        }
    }

    @Nested
    @DisplayName("DELETE /customer/{id}")
    class Delete {

        @Test
        @DisplayName("returns 204 No Content on successful delete")
        void existingId_returns204() throws Exception {
            doNothing().when(customerUseCase).delete(1);

            mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isNoContent());

            verify(customerUseCase).delete(1);
        }

        @Test
        @DisplayName("delegates to use case with correct id")
        void existingId_delegatesWithCorrectId() throws Exception {
            doNothing().when(customerUseCase).delete(1);

            mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isNoContent());

            verify(customerUseCase).delete(1);
            verify(customerUseCase, never()).delete(99);
        }
    }
}