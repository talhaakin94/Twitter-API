package com.app.twitterapi.service;
import com.app.twitterapi.dao.RoleRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Role;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Optional<Role> optional = roleRepository.findByCode("USER");
        Role role = new Role();
        if(optional.isPresent()) {
            role = roleRepository.findByCode(optional.get().getCode()).orElseThrow();
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
