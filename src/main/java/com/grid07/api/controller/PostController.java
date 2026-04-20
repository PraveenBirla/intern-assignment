package com.grid07.api.controller;


import com.grid07.api.entity.Comment;
import com.grid07.api.entity.Post;
import com.grid07.api.repository.CommentRepository;
import com.grid07.api.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }

    @PostMapping("/{postId}/comments")
    public Comment addComment(
            @PathVariable Long postId,
            @RequestBody Comment comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        return "Liked";
    }
}
