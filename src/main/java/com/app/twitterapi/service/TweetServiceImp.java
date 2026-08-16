package com.app.twitterapi.service;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.dto.TweetRequest;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImp implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    @Autowired
    public TweetServiceImp(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Tweet create(TweetRequest tweetRequest) {
        if(tweetRequest.userId() == null || tweetRequest.tweet() == null) {
            throw new RuntimeException("invalid request");
        }
        Optional<User> optional = userRepository.findById(tweetRequest.userId());
        if(optional.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Tweet tweet = tweetRequest.tweet();
        tweet.setUser(optional.get());
        tweet.getUser().getTweets().add(tweet);
        return tweetRepository.save(tweet);
    }
    @Override
    public Tweet findById(Long id){
        Optional<Tweet> optional = tweetRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("tweet not found");
    }
    @Override
    public List<Tweet> listByUserId(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isPresent()) {
            return optional.get().getTweets();
        }
        throw new RuntimeException("user not found");
    }
    @Override
    public Tweet updateTweet(Long id, String message) {
        Optional<Tweet> optional = tweetRepository.findById(id);
        if(optional.isPresent()) {
            optional.get().setMessage(message);
            return tweetRepository.save(optional.get());
        }
        throw new RuntimeException("tweet not found");
    }
    @Override
    public Tweet deleteTweet(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new RuntimeException("user is not authenticated");
        }
        String email = authentication.getName();
        Optional<Tweet> optional = tweetRepository.findById(id);
        if(optional.isPresent()) {
            if(email.isBlank()) {
                throw new RuntimeException("invalid email");
            } else if(!email.equals(optional.get().getUser().getEmail())) {
                throw new RuntimeException("unauthorized user");
            }
            optional.get().getUser().getTweets().remove(optional.get());
            tweetRepository.delete(optional.get());
            return optional.get();
        }
        throw new RuntimeException("tweet not found");
    }
}
