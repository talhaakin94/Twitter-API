package com.app.twitterapi.controller;
import com.app.twitterapi.entity.Comment;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import com.app.twitterapi.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @MockitoBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void create() throws Exception {
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
        when(commentService.create(1L, 1L, "bye")).thenReturn(comment);
        mockMvc.perform(post("/comment/{tweetId}/{userId}", 1L, 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("bye")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bye"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
        verify(commentService).create(1L, 1L, "bye");
    }
    @Test
    void update() throws Exception {
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
        when(commentService.update(1L, "bye")).thenReturn(comment);
        mockMvc.perform(put("/comment/{id}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("bye")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bye"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
        verify(commentService).update(1L, "bye");
    }
    @Test
    void remove() throws Exception {
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
        when(commentService.delete(1L)).thenReturn(comment);
        mockMvc.perform(delete("/comment/{id}", 1L)
                        .with(csrf())
                        .with(user("user@gmail.com"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bye"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
        verify(commentService).delete(1L);
    }
    public static String jsonToString(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch(Exception exception) {
            throw new RuntimeException();
        }
    }
}