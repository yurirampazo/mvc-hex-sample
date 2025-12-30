package com.model.mvc.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  private String name;
  @NotNull
  private String email;
  private Integer age;
  @NotNull
  private LocalDate birthDate;
  @NotNull
  @Valid
  @ManyToMany
  @JoinTable(
    name = "customer_address",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "address_id")
  )
  private Set<Address> addresses;
}
