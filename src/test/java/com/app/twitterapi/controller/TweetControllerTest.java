package com.app.twitterapi.controller;
import com.app.twitterapi.entity.Like;
import com.app.twitterapi.entity.Retweet;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import com.app.twitterapi.service.TweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
class TweetControllerTest {
    @MockitoBean
    private TweetService tweetService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void getById() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new LinkedList<>();
        List<Retweet> retweets = new LinkedList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(tweetService.findById(1L)).thenReturn(tweet);
        mockMvc.perform(get("/tweet/{id}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(tweet))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("hi"));
        verify(tweetService).findById(1L);
    }
    @Test
    void listByUserId() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new LinkedList<>();
        List<Retweet> retweets = new LinkedList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        when(tweetService.listByUserId(1L)).thenReturn(tweets);
        mockMvc.perform(get("/tweet/user/{userId}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(tweets))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("hi"));
        verify(tweetService).listByUserId(1L);
    }
    @Test
    void postTweet() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new LinkedList<>();
        List<Retweet> retweets = new LinkedList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(tweetService.create(tweet)).thenReturn(tweet);
        mockMvc.perform(post("/tweet")
                        .with(csrf())
                        .with(user("user@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(tweet))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("hi"));
        verify(tweetService).create(tweet);
    }
    @Test
    void updateTweet() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new LinkedList<>();
        List<Retweet> retweets = new LinkedList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(tweetService.updateTweet(1L, "hi")).thenReturn(tweet);
        mockMvc.perform(put("/tweet/{id}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("hi")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("hi"));
        verify(tweetService).updateTweet(1L, "hi");
    }
    @Test
    void deleteTweet() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new LinkedList<>();
        List<Retweet> retweets = new LinkedList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(tweetService.deleteTweet(1L)).thenReturn(tweet);
        mockMvc.perform(delete("/tweet/{id}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("hi"));
        verify(tweetService).deleteTweet(1L);
    }
    public static String jsonToString(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch(Exception exception) {
            throw new RuntimeException();
        }
    }
}