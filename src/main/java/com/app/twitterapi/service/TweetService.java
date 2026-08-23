package com.app.twitterapi.service;
import com.app.twitterapi.dto.TweetRequest;
import com.app.twitterapi.entity.Tweet;
import java.util.List;

public interface TweetService {
    List<Tweet> findAll();
    Tweet create(TweetRequest tweetRequest);
    Tweet findById(Long id);
    List<Tweet> listByUserId(Long userId);
    Tweet updateTweet(Long id, String message);
    Tweet deleteTweet(Long id);
    List<Tweet> findAllReact();
}
