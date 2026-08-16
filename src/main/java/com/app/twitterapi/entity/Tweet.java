package com.app.twitterapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String message;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "tweet")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "tweet")
    private List<Like> likes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "tweet")
    private List<Retweet> retweets = new ArrayList<>();
}
