package com.model.mvc.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponseDTO(
    @JsonProperty("cep") String zipcode,
    @JsonProperty("logradouro") String street,
    @JsonProperty("complemento") String complement,
    @JsonProperty("unidade") String unit,
    @JsonProperty("bairro") String neighborhood,
    @JsonProperty("localidade") String city,
    @JsonProperty("uf") String stateCode,
    @JsonProperty("estado") String stateName,
    @JsonProperty("regiao") String region,
    @JsonProperty("ibge") String ibgeCode,
    @JsonProperty("gia") String giaCode,
    @JsonProperty("ddd") String ddd,
    @JsonProperty("siafi") String siafiCode
) {}
