package com.grid07.api.service;

import com.grid07.api.dto.CommentRequestDTO;
import com.grid07.api.dto.CommentResponseDTO;
import com.grid07.api.entity.Comment;
import com.grid07.api.entity.Post;
import com.grid07.api.repository.CommentRepository;
import com.grid07.api.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ViralityService viralityService;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, ViralityService viralityService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.viralityService = viralityService;
    }

    public CommentResponseDTO addComment(Long postId, CommentRequestDTO dto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (dto.getDepthLevel() > 20) {
            throw new RuntimeException("Depth limit exceeded");
        }

        Comment comment = Comment.builder()
                .post(post)
                .authorId(dto.getAuthorId())
                .authorType(dto.getAuthorType())
                .content(dto.getContent())
                .depthLevel(dto.getDepthLevel())
                .build();

        Comment saved = commentRepository.save(comment);


        if (dto.getAuthorType() == Post.AuthorType.BOT) {
            viralityService.addBotReply(postId);
        } else {
            viralityService.addComment(postId);
        }

        return CommentResponseDTO.builder()
                .id(saved.getId())
                .postId(postId)
                .authorId(saved.getAuthorId())
                .authorType(saved.getAuthorType().name())
                .content(saved.getContent())
                .depthLevel(saved.getDepthLevel())
                .createdAt(saved.getCreatedAt())
                .build();
    }

}
