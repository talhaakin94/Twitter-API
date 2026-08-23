package com.app.twitterapi.service;
import com.app.twitterapi.dao.LikeRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Like;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LikeServiceImp implements LikeService {
    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    @Autowired
    public LikeServiceImp(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Tweet like(Long tweetId, Long userId) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(tweetId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalTweet.isEmpty()) {
            throw new RuntimeException("tweet not found");
        }
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Like like = new Like();
        like.setTweet(optionalTweet.get());
        like.setUser(optionalUser.get());
        optionalUser.get().getLikes().add(like);
        optionalTweet.get().getLikes().add(like);
        likeRepository.save(like);
        return like.getTweet();
    }
    @Override
    public Tweet disLike(Long tweetId, Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new RuntimeException("user is not authenticated");
        }
        String email = authentication.getName();
        Optional<Like> optional = likeRepository.findByTweetIdAndUserId(tweetId, userId);
        if(optional.isPresent()) {
            if(email.isBlank()) {
                throw new RuntimeException("invalid email");
            } else if(!email.equals(optional.get().getUser().getEmail()) && !email.equals(optional.get().getTweet().getUser().getEmail())) {
                throw new RuntimeException("unauthorized user");
            }
            optional.get().getUser().getLikes().remove(optional.get());
            optional.get().getTweet().getLikes().remove(optional.get());
            likeRepository.delete(optional.get());
            return optional.get().getTweet();
        }
        throw new RuntimeException("like not found");
    }
}
