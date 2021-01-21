package com.nguyen.demo.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence_generator")
    @SequenceGenerator(name = "product_sequence_generator",
            sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String name;
    private Long price;
    private String brand;
    private String colour;
    private Long quantity;
}
