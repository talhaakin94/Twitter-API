package com.app.twitterapi.controller;
import com.app.twitterapi.dto.LikeResponse;
import com.app.twitterapi.dto.TweetResponse;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.service.LikeService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class LikeController {
    private final LikeService likeService;
    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @PostMapping("/like/{tweetId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse like(@Positive @PathVariable Long tweetId, @Positive @PathVariable Long userId) {
        Tweet tweet = likeService.like(tweetId, userId);
        return new LikeResponse(new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size()));
    }
    @PostMapping("/dislike/{tweetId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse dislike(@Positive @PathVariable Long tweetId, @Positive @PathVariable Long userId) {
        Tweet tweet = likeService.disLike(tweetId, userId);
        return new LikeResponse(new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size()));
    }
}
