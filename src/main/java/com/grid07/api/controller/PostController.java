package com.grid07.api.controller;


import com.grid07.api.dto.*;
import com.grid07.api.entity.Comment;
import com.grid07.api.entity.Post;
import com.grid07.api.repository.CommentRepository;
import com.grid07.api.repository.PostRepository;
import com.grid07.api.service.CommentService;
import com.grid07.api.service.LikeService;
import com.grid07.api.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

     private final PostService postService;
     private final CommentService commentService;
     private final LikeService likeService;


    public PostController( PostService postService, CommentService commentService, LikeService likeService) {
        this.postService = postService;

        this.commentService = commentService;

        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponseDTO> createPost(@RequestBody  CreatePostRequestDTO dto){
        return  ResponseEntity.ok(postService.createPost(dto));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDTO dto) {

        try {
            return ResponseEntity.ok(commentService.addComment(postId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{postId}/like")
    public  ResponseEntity<LikeResponseDTO> likePost(@PathVariable Long postId,
                                                     @RequestBody LikeRequestDTO dto) {
        return ResponseEntity.ok(
                likeService.likePost(postId,dto.getUserId())
        );
    }

}
