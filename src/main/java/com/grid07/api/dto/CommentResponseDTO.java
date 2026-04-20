package com.grid07.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {

    private Long id;
    private Long postId;
    private Long authorId;
    private String authorType;
    private String content;
    private int depthLevel;
    private LocalDateTime createdAt;

}
