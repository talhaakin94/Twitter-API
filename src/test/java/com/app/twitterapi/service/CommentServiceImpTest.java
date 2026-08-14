package com.app.twitterapi.service;
import com.app.twitterapi.dao.CommentRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Comment;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentServiceImpTest {
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
       commentService = new CommentServiceImp(commentRepository, tweetRepository, userRepository);
    }
    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
    }
    @Test
    @DisplayName("create comment by tweet id and user id")
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
        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser(user);
        List<Comment> comments = new ArrayList<>();
        user.setComments(comments);
        given(tweetRepository.findById(1L)).willReturn(Optional.of(tweet));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        commentService.create(1L, 1L, "welcome");
        verify(commentRepository).save(any(Comment.class));
    }
    @Test
    @DisplayName("update comment by comment id")
    void update() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setId(1L);
        comment.setMessage("bye");
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        commentService.update(1L, "welcome");
        verify(commentRepository).save(comment);
    }
    @Test
    @DisplayName("delete comment by comment id")
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
        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setId(1L);
        List<Comment> comments = new ArrayList<>();
        user.setComments(comments);
        Authentication authentication = mock(Authentication.class);
        given(authentication.getName()).willReturn("user@gmail.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        commentService.delete(1L);
        verify(commentRepository).delete(comment);
    }
}