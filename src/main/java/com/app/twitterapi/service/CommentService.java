package com.app.twitterapi.service;
import com.app.twitterapi.entity.Comment;

public interface CommentService {
        Comment create(Long tweetId, Long userId, String message);
        Comment update(Long id, String message);
        Comment delete(Long id);
}
