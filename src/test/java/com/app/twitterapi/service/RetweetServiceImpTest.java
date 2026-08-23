package com.app.twitterapi.service;
import com.app.twitterapi.dao.RetweetRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Retweet;
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
class RetweetServiceImpTest {
    private RetweetService retweetService;
    @Mock
    private RetweetRepository retweetRepository;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        retweetService = new RetweetServiceImp(retweetRepository, tweetRepository, userRepository);
    }
    @AfterEach
    void tearDown() {
        retweetRepository.deleteAll();
    }
    @Test
    @DisplayName("create retweet by tweet id and user id")
    void retweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        Retweet retweet = new Retweet();
        retweet.setTweet(tweet);
        retweet.setUser(user);
        List<Retweet> retweets = new ArrayList<>();
        user.setRetweets(retweets);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        retweetService.retweet(1L, 1L);
        verify(retweetRepository).save(retweet);
    }
    @Test
    @DisplayName("delete retweet by retweet id")
    void delete() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        Retweet retweet = new Retweet();
        retweet.setTweet(tweet);
        retweet.setUser(user);
        retweet.setId(1L);
        List<Retweet> retweets = new ArrayList<>();
        user.setRetweets(retweets);
        Authentication authentication = mock(Authentication.class);
        given(authentication.getName()).willReturn("user@gmail.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(retweetRepository.findById(1L)).willReturn(Optional.of(retweet));
        retweetService.delete(1L);
        verify(retweetRepository).delete(retweet);
    }
}