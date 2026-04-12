package com.model.hex.infrastructure.web.controller;

import com.model.hex.domain.ports.out.AddressClientPort;
import com.model.hex.infrastructure.web.dto.AddressResponseDTO;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver"
})
@DisplayName("AddressController — integration tests")
class AddressControllerIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockitoBean
  private AddressClientPort addressClient;

  private MockMvc mockMvc;
  private AddressResponseDTO viaCepResponse;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    viaCepResponse = new AddressResponseDTO();
    viaCepResponse.setZipCode("01310-100");
    viaCepResponse.setStreetName("Avenida Paulista");
  }

  @Nested
  @DisplayName("GET /address/{zipCode}")
  class GetByZipCode {

    @Test
    @DisplayName("returns 200 OK for a valid zip code")
    void validZip_returns200() throws Exception {
      when(addressClient.findByZipCode("01310-100")).thenReturn(viaCepResponse);

      mockMvc.perform(get("/address/01310-100"))
          .andExpect(status().isOk());

      verify(addressClient).findByZipCode("01310-100");
    }

    @Test
    @DisplayName("response content-type is application/json")
    void validZip_returnsJson() throws Exception {
      when(addressClient.findByZipCode("01310-100")).thenReturn(viaCepResponse);

      mockMvc.perform(get("/address/01310-100"))
          .andExpect(content().contentType(MediaType.APPLICATION_JSON));

      verify(addressClient).findByZipCode("01310-100");
    }

    @Test
    @DisplayName("response body contains zipCode and streetName")
    void validZip_returnsCorrectBody() throws Exception {
      when(addressClient.findByZipCode("01310-100")).thenReturn(viaCepResponse);

      mockMvc.perform(get("/address/01310-100"))
          .andExpect(jsonPath("$.zipCode").value("01310-100"))
          .andExpect(jsonPath("$.streetName").value("Avenida Paulista"));

      verify(addressClient).findByZipCode("01310-100");
    }

    @Test
    @DisplayName("blank zip code returns 500 (no exception handler yet)")
    void blankZip_returns500() throws Exception {
      mockMvc.perform(get("/address/ "))
          .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("external client failure propagates as 500")
    void clientThrows_returns500() throws Exception {
      when(addressClient.findByZipCode("00000-000"))
          .thenThrow(new RuntimeException("ViaCEP unavailable"));

      mockMvc.perform(get("/address/00000-000"))
          .andExpect(status().isInternalServerError());

      verify(addressClient).findByZipCode("00000-000");
    }
  }
}