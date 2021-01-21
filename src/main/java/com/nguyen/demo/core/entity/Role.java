package com.nguyen.demo.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "roles")
@Entity
public class Role {

    private static final int BATCH_SIZE = 20;
    private static final int NAME_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_sequence_generator")
    @SequenceGenerator(name = "roles_sequence_generator", sequenceName = "roles_sequence_generator", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    @Column(length = NAME_MAX_LENGTH)
    private String name;
}
