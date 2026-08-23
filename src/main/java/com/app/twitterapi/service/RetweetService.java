package com.app.twitterapi.service;
import com.app.twitterapi.entity.Retweet;

public interface RetweetService {
    Retweet retweet(Long tweetId, Long userId);
    Retweet delete(Long id);
    Retweet findByTweetIdAndUserId(Long tweetId, Long userId);
}
