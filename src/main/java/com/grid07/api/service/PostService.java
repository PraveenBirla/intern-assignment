package com.grid07.api.service;

import com.grid07.api.dto.CreatePostRequestDTO;
import com.grid07.api.dto.CreatePostResponseDTO;
import com.grid07.api.entity.Post;
import com.grid07.api.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public CreatePostResponseDTO createPost(CreatePostRequestDTO dto) {

        Post post = Post.builder()
                .authorId(dto.getAuthorId())
                .authorType(dto.getAuthorType())
                .content(dto.getContent())
                .build();

        Post saved = postRepository.save(post);

        return CreatePostResponseDTO.builder()
                .id(saved.getId())
                .authorId(saved.getAuthorId())
                .authorType(saved.getAuthorType().name())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
