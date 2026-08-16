package com.app.twitterapi.service;
import com.app.twitterapi.dao.RetweetRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Retweet;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RetweetServiceImp implements RetweetService{
    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    @Autowired
    public RetweetServiceImp(RetweetRepository retweetRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.retweetRepository = retweetRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Retweet retweet(Long tweetId, Long userId) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(tweetId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalTweet.isEmpty()) {
            throw new RuntimeException("tweet not found");
        }
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Retweet retweet = new Retweet();
        retweet.setTweet(optionalTweet.get());
        retweet.setUser(optionalUser.get());
        optionalUser.get().getRetweets().add(retweet);
        optionalTweet.get().getRetweets().add(retweet);
        return retweetRepository.save(retweet);
    }
    @Override
    public Retweet delete(Long id) {
        Optional<Retweet> optional = retweetRepository.findById(id);
        if(optional.isPresent()) {
            optional.get().getUser().getRetweets().remove(optional.get());
            optional.get().getTweet().getRetweets().remove(optional.get());
            retweetRepository.delete(optional.get());
            return optional.get();
        }
        throw new RuntimeException("retweet not found");
    }
}
