package com.app.twitterapi.dao;
import com.app.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTweetIdAndUserId(Long tweetId, Long userId);
}
