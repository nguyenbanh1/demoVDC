package com.nguyen.demo.core.repository;

import com.nguyen.demo.core.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
