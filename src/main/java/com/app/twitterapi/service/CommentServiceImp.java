package com.app.twitterapi.service;
import com.app.twitterapi.dao.CommentRepository;
import com.app.twitterapi.dao.TweetRepository;
import com.app.twitterapi.dao.UserRepository;
import com.app.twitterapi.entity.Comment;
import com.app.twitterapi.entity.Tweet;
import com.app.twitterapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    @Autowired
    public CommentServiceImp(CommentRepository commentRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Comment create(Long tweetId, Long userId, String message) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(tweetId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalTweet.isEmpty()) {
            throw new RuntimeException("tweet not found");
        }
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Comment comment = new Comment();
        comment.setUser(optionalUser.get());
        comment.setTweet(optionalTweet.get());
        comment.setMessage(message);
        optionalUser.get().getComments().add(comment);
        return commentRepository.save(comment);
    }
    @Override
    public Comment update(Long id, String message) {
        Optional<Comment> optional = commentRepository.findById(id);
        if(optional.isPresent()) {
            optional.get().setMessage(message);
            return commentRepository.save(optional.get());
        }
        throw new RuntimeException("comment not found");
    }
    @Override
    public Comment delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new RuntimeException("user is not authenticated");
        }
        String email = authentication.getName();
        Optional<Comment> optional = commentRepository.findById(id);
        if(optional.isPresent()) {
            if(email.isBlank()) {
                throw new RuntimeException("invalid email");
            } else if(!email.equals(optional.get().getUser().getEmail()) && !email.equals(optional.get().getTweet().getUser().getEmail())) {
                throw new RuntimeException("unauthorized user");
            }
            optional.get().getUser().getComments().remove(optional.get());
            commentRepository.delete(optional.get());
            return optional.get();
        }
        throw new RuntimeException("comment not found");
    }
}
