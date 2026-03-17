package com.model.mvc.infrastructure.client;

import com.model.mvc.infrastructure.web.dto.ViaCepResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ViaCepClient {

  private static final String GET_ADDRESS_JSON_ENDPOINT = "/ws/{zipCode}/json";

  private final RestClient restClient;

  public ViaCepClient(RestClient.Builder builder,
                      @Value("${via-cep.base-url}") String baseUrl) {
    this.restClient = builder
        .baseUrl(baseUrl)
        .build();
  }

//  @Retryable(
//      includes = {ResourceAccessException.class},
//      maxRetries = 3,
//      delay = 1000,
//      multiplier = 2
//  )
//  @ConcurrencyLimit(10)
  public ViaCepResponseDTO getViaCepAddress(String zipCode) {
    return restClient.get()
        .uri(GET_ADDRESS_JSON_ENDPOINT, zipCode)
        .retrieve()
        .body(ViaCepResponseDTO.class);
  }
}