package com.nguyen.demo.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "person")
@Entity
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence_generator")
    @SequenceGenerator(name = "person_sequence_generator",
            sequenceName = "person_sequence", allocationSize = 1)
    private Long id;
    private String lastName;
    private String firstName;
}
