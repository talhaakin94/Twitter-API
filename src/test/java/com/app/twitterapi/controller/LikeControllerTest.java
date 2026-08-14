package com.app.twitterapi.controller;
import com.app.twitterapi.entity.Like;
import com.app.twitterapi.entity.Retweet;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import com.app.twitterapi.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {
    @MockitoBean
    private LikeService likeService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void like() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new ArrayList<>();
        List<Retweet> retweets = new ArrayList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(likeService.like(1L,1L)).thenReturn(tweet);
        mockMvc.perform(post("/like/{tweetId}/{userId}", 1L, 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tweetResponse.message").value("hi"));
        verify(likeService).like(1L, 1L);
    }
    @Test
    void dislike() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hi");
        User user = new User();
        user.setId(1L);
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("1234");
        tweet.setUser(user);
        List<Like> likes = new ArrayList<>();
        List<Retweet> retweets = new ArrayList<>();
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        when(likeService.disLike(1L, 1L)).thenReturn(tweet);
        mockMvc.perform(post("/dislike/{tweetId}/{userId}", 1L, 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tweetResponse.message").value("hi"));
        verify(likeService).disLike(1L, 1L);
    }
    public static String jsonToString(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch(Exception exception) {
            throw new RuntimeException();
        }
    }
}