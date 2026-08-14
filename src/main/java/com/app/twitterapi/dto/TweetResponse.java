package com.app.twitterapi.dto;

public record TweetResponse(String userName, String message, Integer likes, Integer retweets) {
}
