package com.model.mvc.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver"
})
@DisplayName("AddressController Integration Tests")
class AddressControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    @DisplayName("Should get address endpoint exists")
    void testAddressEndpointExists() throws Exception {
        mockMvc.perform(get("/address/01310100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Should return JSON response for valid zip code")
    void testGetByZipCodeReturnsJson() throws Exception {
        mockMvc.perform(get("/address/01310100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.zipCode").exists());
    }
    @Test
    @DisplayName("Should handle zip code without hyphen")
    void testGetByZipCodeWithoutHyphen() throws Exception {
        mockMvc.perform(get("/address/01310100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Should handle zip code with hyphen")
    void testGetByZipCodeWithHyphen() throws Exception {
        mockMvc.perform(get("/address/01310-100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.zipCode").exists());
    }
    @Test
    @DisplayName("Should return error for blank zip code")
    void testGetByZipCodeBlank() throws Exception {
        mockMvc.perform(get("/address/ ")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
    @Test
    @DisplayName("Should handle multiple requests")
    void testMultipleRequests() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/address/01310100")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
    }
    @Test
    @DisplayName("Should return 200 OK status")
    void testStatusOk() throws Exception {
        mockMvc.perform(get("/address/01310100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Should return JSON content type")
    void testContentType() throws Exception {
        mockMvc.perform(get("/address/01310100")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
