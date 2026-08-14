package com.app.twitterapi.service;
import com.app.twitterapi.entity.Tweet;

public interface LikeService {
    Tweet like(Long tweetId, Long userId);
    Tweet disLike(Long tweetId, Long userId);
}
