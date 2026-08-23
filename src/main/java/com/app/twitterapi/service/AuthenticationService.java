package com.app.twitterapi.service;
import com.app.twitterapi.dao.RoleRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Role;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public User register(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role role = roleRepository.findByCode("USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setCode("USER");
            return roleRepository.save(newRole);
        });
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public User login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return userRepository.findUser(email).orElseThrow();
    }
}
