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
    private final GuardrailService guardrailService;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, ViralityService viralityService, GuardrailService guardrailService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.viralityService = viralityService;
        this.guardrailService = guardrailService;
    }

    public CommentResponseDTO addComment(Long postId, CommentRequestDTO dto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        guardrailService.validateDepth(dto.getDepthLevel());

        boolean isBot = dto.getAuthorType() == Post.AuthorType.BOT;

        if (isBot) {


            if (!guardrailService.checkBotLimit(postId)) {
                throw new RuntimeException("Bot limit exceeded (429)");
            }


            Long botId = dto.getAuthorId();
            Long userId = post.getAuthorId();

            if (!guardrailService.checkCooldown(botId, userId)) {
                throw new RuntimeException("Cooldown active (429)");
            }
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
