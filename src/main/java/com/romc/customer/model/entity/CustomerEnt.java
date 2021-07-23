package com.romc.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "the numbreid is not vacio")
    @Size(min = 8, max = 8, message = "el maximo del number is 8")
    @Column(name = "number_id", unique = true, length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "el name is not vacio")
    @Column(name = "first_name", nullable = false)
    private String firstname;

    @NotEmpty(message = "el name is not vacio")
    @Column(name = "last_name", nullable = false)
    private String lastname;

    @NotEmpty(message = "el name is not vacio")
    @Email(message = "no es una direccion de correo bien formada")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photourl;

    @NotNull(message = "the region no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private RegionEnt regionEnt;

    private String state;
}
