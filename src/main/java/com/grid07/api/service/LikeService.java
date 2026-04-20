package com.grid07.api.service;

import com.grid07.api.dto.LikeResponseDTO;
import com.grid07.api.entity.Post;
import com.grid07.api.entity.PostLike;
import com.grid07.api.repository.PostLikeRepository;
import com.grid07.api.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final ViralityService viralityService;
    private final PostRepository postRepository;

    public LikeService(PostLikeRepository postLikeRepository, ViralityService viralityService, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.viralityService = viralityService;
        this.postRepository = postRepository;
    }

    public LikeResponseDTO likePost(Long postId, Long userId) {

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new RuntimeException("Already liked");
        }

        Post post = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("post not found"));

        PostLike like = PostLike.builder()
                .post(post)
                .userId(userId)
                .build();

        postLikeRepository.save(like);

        viralityService.addLike(postId);

        return LikeResponseDTO.builder()
                .message("Post liked successfully")
                .postId(postId)
                .userId(userId)
                .build();
    }

}
