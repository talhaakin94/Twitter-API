package com.app.twitterapi.service;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.dto.TweetRequest;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TweetServiceImpTest {
    private TweetService tweetService;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImp(tweetRepository, userRepository);
    }
    @AfterEach
    void tearDown() {
        tweetRepository.deleteAll();
    }
    @Test
    @DisplayName("find all tweets")
    void findAll() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        tweetService.findAll();
    }
    @Test
    @DisplayName("create a tweet")
    void create() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        tweetService.create(new TweetRequest(1L, tweet));
        verify(tweetRepository).save(tweet);
    }
    @Test
    @DisplayName("find tweet by tweet id")
    void findById() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        tweetService.findById(1L);
        verify(tweetRepository).findById(1L);
    }
    @Test
    @DisplayName("find tweet list by user id")
    void listByUserId() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        tweetService.listByUserId(1L);
        verify(userRepository).findById(1L);
    }
    @Test
    @DisplayName("update tweet by tweet id")
    void updateTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        tweetService.updateTweet(1L, "welcome");
        verify(tweetRepository).save(tweet);
    }
    @Test
    @DisplayName("delete tweet by tweet id")
    void deleteTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Tweet> tweets = new ArrayList<>();
        user.setTweets(tweets);
        Authentication authentication = mock(Authentication.class);
        given(authentication.getName()).willReturn("user@gmail.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        tweetService.deleteTweet(1L);
        verify(tweetRepository).delete(tweet);
    }
}