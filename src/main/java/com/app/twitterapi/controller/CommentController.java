package com.app.twitterapi.controller;
import com.app.twitterapi.dto.CommentResponse;
import com.app.twitterapi.entity.Comment;
import com.app.twitterapi.service.CommentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/{tweetId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@Positive @PathVariable Long tweetId, @Positive @PathVariable Long userId, @NotBlank @RequestBody String message) {
        Comment comment = commentService.create(tweetId, userId, message);
        return new CommentResponse(comment.getMessage(), comment.getUser().getName());
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse update(@Positive @PathVariable Long id, @NotBlank @RequestBody String message) {
        Comment comment = commentService.update(id, message);
        return new CommentResponse(comment.getMessage(), comment.getUser().getName());
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse remove(@Positive @PathVariable Long id) {
        Comment comment = commentService.delete(id);
        return new CommentResponse(comment.getMessage(), comment.getUser().getName());
    }
}
