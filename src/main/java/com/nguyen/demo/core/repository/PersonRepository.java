package com.nguyen.demo.core.repository;

import com.nguyen.demo.core.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
