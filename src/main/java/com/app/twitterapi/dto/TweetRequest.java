package com.app.twitterapi.dto;
import com.app.twitterapi.entity.Tweet;

public record TweetRequest(Long userId, Tweet tweet) {
}
