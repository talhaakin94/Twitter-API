package com.app.twitterapi.service;
import com.app.twitterapi.dao.LikeRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Like;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LikeServiceImpTest {
    private LikeService likeService;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        likeService = new LikeServiceImp(likeRepository, tweetRepository, userRepository);
    }
    @AfterEach
    void tearDown() {
        likeRepository.deleteAll();
    }
    @Test
    @DisplayName("create like by tweet id and user id")
    void like() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);
        List<Like> likes = new ArrayList<>();
        user.setLikes(likes);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        likeService.like(1L, 1L);
        verify(likeRepository).save(like);
    }
    @Test
    @DisplayName("delete like by tweet id and user id")
    void disLike() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);
        List<Like> likes = new ArrayList<>();
        user.setLikes(likes);
        given(likeRepository.findByTweetIdAndUserId(1L, 1L)).willReturn(Optional.of(like));
        likeService.disLike(1L, 1L);
        verify(likeRepository).delete(like);
    }
}