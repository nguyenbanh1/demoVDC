package com.nguyen.demo.customer;

import com.nguyen.demo.authentication.SecurityUtils;
import com.nguyen.demo.core.entity.Customer;
import com.nguyen.demo.core.repository.UserRepository;
import com.nguyen.demo.error.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final UserRepository userRepository;

    public Customer currentLogin() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .orElseThrow(() -> new DataNotFoundException("Can't find current employee."))
                .getCustomer();
    }
}
