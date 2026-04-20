package com.grid07.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreatePostResponseDTO {

    private Long id;
    private Long authorId;
    private String authorType;
    private String content;
    private LocalDateTime createdAt;
}
