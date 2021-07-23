package com.romc.customer.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_regions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
