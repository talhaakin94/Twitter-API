package com.app.twitterapi.controller;
import com.app.twitterapi.dto.RetweetResponse;
import com.app.twitterapi.dto.TweetResponse;
import com.app.twitterapi.entity.Retweet;
import com.app.twitterapi.service.RetweetService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/retweet")
public class RetweetController {
    private final RetweetService retweetService;
    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }
    @GetMapping("/{tweetId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public RetweetResponse retweet(@Positive @PathVariable Long tweetId, @Positive @PathVariable Long userId) {
        Retweet retweet = retweetService.retweet(tweetId, userId);
        return new RetweetResponse(new TweetResponse(retweet.getTweet().getUser().getName(), retweet.getTweet().getMessage(), retweet.getTweet().getLikes().size(), retweet.getTweet().getRetweets().size()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetweetResponse deleteRetweet(@Positive @PathVariable Long id) {
        Retweet retweet = retweetService.delete(id);
        return new RetweetResponse(new TweetResponse(retweet.getTweet().getUser().getName(), retweet.getTweet().getMessage(), retweet.getTweet().getLikes().size(), retweet.getTweet().getRetweets().size()));
    }
}
