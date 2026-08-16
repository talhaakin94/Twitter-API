package com.app.twitterapi.controller;
import com.app.twitterapi.dto.TweetRequest;
import com.app.twitterapi.dto.TweetResponse;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.service.TweetService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
@RequestMapping("/tweet")
public class TweetController {
    private final TweetService tweetService;
    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse getById(@Positive @PathVariable Long id) {
        Tweet tweet = tweetService.findById(id);
        return new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size());
    }
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponse> listByUserId(@Positive @PathVariable Long userId) {
        return tweetService.listByUserId(userId).stream().map(tweet -> new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size())).toList();
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponse postTweet(@RequestBody TweetRequest tweetRequest) {
        tweetService.create(tweetRequest);
        return new TweetResponse(tweetRequest.tweet().getUser().getName(), tweetRequest.tweet().getMessage(), tweetRequest.tweet().getLikes().size(), tweetRequest.tweet().getRetweets().size());
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse updateTweet(@Positive @PathVariable Long id, @NotBlank @RequestBody String message) {
        Tweet tweet = tweetService.updateTweet(id, message);
        return new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size());
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse deleteTweet(@Positive @PathVariable Long id) {
        Tweet tweet = tweetService.deleteTweet(id);
        return new TweetResponse(tweet.getUser().getName(), tweet.getMessage(), tweet.getLikes().size(), tweet.getRetweets().size());
    }
}
