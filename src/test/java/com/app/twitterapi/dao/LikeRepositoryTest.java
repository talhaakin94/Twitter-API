package com.app.twitterapi.dao;
import com.app.twitterapi.entity.Like;
import com.app.twitterapi.entity.Tweet;
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
class LikeRepositoryTest {
    private final LikeRepository likeRepository;
    @Autowired
    public LikeRepositoryTest(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
        likeRepository.deleteAll();
    }
    @Test
    @DisplayName("find like by tweet id and user id")
    void findByTweetIdAndUserId() {
        User user = new User();
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        Tweet tweet = new Tweet();
        tweet.setMessage("hi");
        tweet.setUser(user);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);
        likeRepository.save(like);
        Long tweetId = like.getTweet().getId();
        Long userId = like.getUser().getId();
        Optional<Like> optional = likeRepository.findByTweetIdAndUserId(tweetId, userId);
        assertTrue(optional.isPresent());
        assertEquals(tweetId, optional.get().getTweet().getId());
        assertEquals(userId, optional.get().getUser().getId());
        assertEquals("user", optional.get().getUser().getName());
    }
}