package com.app.twitterapi.dao;
import com.app.twitterapi.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    private final UserRepository userRepository;
    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        Optional<User> optional = userRepository.findUser("user@gmail.com");
        if(optional.isEmpty()) {
            userRepository.save(user);
        }
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("find user by user email")
    void findUser() {
        Optional<User> optional = userRepository.findUser("user@gmail.com");
        assertTrue(optional.isPresent());
        assertEquals("user@gmail.com", optional.get().getEmail());
        assertEquals("user", optional.get().getName());
    }
}