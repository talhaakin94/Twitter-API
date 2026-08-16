package com.app.twitterapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="retweet")
public class Retweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    @NotNull
    private Tweet tweet;
}
