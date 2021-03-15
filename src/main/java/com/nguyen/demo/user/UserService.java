package com.nguyen.demo.user;

import com.nguyen.demo.authentication.SecurityUtils;
import com.nguyen.demo.core.entity.Customer;
import com.nguyen.demo.core.entity.Role;
import com.nguyen.demo.core.entity.User;
import com.nguyen.demo.core.repository.RoleRepository;
import com.nguyen.demo.core.repository.UserRepository;
import com.nguyen.demo.error.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Transactional
    public UserDto create(RegisterReq registerReq) {
        User user = UserMapper.INSTANCE.of(registerReq);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        log.debug("Create user successfully with email = {}", createdUser.getEmail());
        return UserMapper.INSTANCE.of(createdUser);
    }

    @Transactional
    public UserDto createIfNot(String email, String rawPassword, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new DataNotFoundException("Not found role with name = " + roleName));
        Optional<User> userOpt = userRepository.findOneByEmailIgnoreCase(email);
        if (userOpt.isPresent()) {
            return UserMapper.INSTANCE.of(userOpt.get());
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
//        user.setRoles(Set.of(role));
        User createdUser = userRepository.save(user);
        return UserMapper.INSTANCE.of(createdUser);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found user with id =" + id));
    }

    public User currentLogin() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .orElseThrow(() -> new DataNotFoundException("Can't find current employee."));
    }
}
