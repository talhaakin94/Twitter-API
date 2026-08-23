package com.app.twitterapi.dto;
import java.util.List;

public record TweetResponseReact(Long id, String userName, String message, Integer likes, Integer retweets, Boolean liked, Long retweetId, Boolean retweeted, List<CommentResponseReact> comments) {
}
