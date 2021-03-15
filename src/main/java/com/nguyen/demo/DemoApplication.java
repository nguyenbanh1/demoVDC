package com.nguyen.demo;

import com.nguyen.demo.core.entity.Role;
import com.nguyen.demo.core.entity.RolesConstants;
import com.nguyen.demo.core.entity.User;
import com.nguyen.demo.core.repository.RoleRepository;
import com.nguyen.demo.core.repository.UserRepository;
import com.nguyen.demo.product.CreateProductReq;
import com.nguyen.demo.product.ProductService;
import com.nguyen.demo.user.RegisterReq;
import com.nguyen.demo.user.UserDto;
import com.nguyen.demo.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
@Slf4j
public class DemoApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductService productService;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @Transactional
    InitializingBean initData() {
        return () -> {
            //Create Role
            Role adminRole = new Role();
            adminRole.setName(RolesConstants.ADMIN);
            Role createdAdminRole = roleRepository.save(adminRole);

            Role customerRole = new Role();
            customerRole.setName(RolesConstants.CUSTOMER);
            Role createdCustomerRole = roleRepository.save(customerRole);

            //Create Admin
            RegisterReq adminRegisterReq = new RegisterReq();
            adminRegisterReq.setEmail("nguyen.tang@gmail.com");
            adminRegisterReq.setPassword("123456789");
            UserDto adminUserDto = userService.create(adminRegisterReq);

            User admin = userService.getById(adminUserDto.getId());
//            admin.setRoles(Set.of(createdAdminRole, customerRole));
            userRepository.save(admin);
            //Create Customer
            RegisterReq registerReq = new RegisterReq();
            registerReq.setEmail("tangkhanhnguyen_1986@yahoo.com.vn");
            registerReq.setPassword("123456789");
            UserDto userDto = userService.create(registerReq);

            User user = userService.getById(userDto.getId());
//            user.setRoles(Set.of(createdCustomerRole));
            userRepository.save(user);

            //Create Product
            CreateProductReq createProductReq = new CreateProductReq();
            createProductReq.setName("Anker");
            createProductReq.setBrand("AAAA");
            createProductReq.setColour("Red");
            createProductReq.setPrice(100000L);
            createProductReq.setQuantity(100);
            productService.create(createProductReq);

            CreateProductReq createProductReq1 = new CreateProductReq();
            createProductReq1.setName("Anker OO");
            createProductReq1.setBrand("AAAA");
            createProductReq1.setColour("Red");
            createProductReq1.setPrice(100000L);
            createProductReq1.setQuantity(100);
            productService.create(createProductReq1);

        };
    }
}
