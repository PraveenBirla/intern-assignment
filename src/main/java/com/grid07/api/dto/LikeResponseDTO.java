package com.grid07.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponseDTO {

    private String message;
    private Long postId;
    private Long userId;
}
