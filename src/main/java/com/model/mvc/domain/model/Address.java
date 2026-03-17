package com.model.mvc.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Address {

  private Integer id;
  private String zipCode;
  private String countryCode;
  private String streetName;
  private String neighbourhood;
  private String city;
  private String state;
  private Set<Customer> customers = new HashSet<>();

  public Address() {}

  public Address(Integer id, String zipCode, String countryCode,
                 String streetName, String neighbourhood,
                 String city, String state) {
    this.id = id;
    this.zipCode = zipCode;
    this.countryCode = countryCode;
    this.streetName = streetName;
    this.neighbourhood = neighbourhood;
    this.city = city;
    this.state = state;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Set<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(Set<Customer> customers) {
    this.customers = customers;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getNeighbourhood() {
    return neighbourhood;
  }

  public void setNeighbourhood(String neighbourhood) {
    this.neighbourhood = neighbourhood;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}