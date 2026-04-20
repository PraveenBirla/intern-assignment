package com.grid07.api.dto;

import com.grid07.api.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequestDTO {
    private Long authorId;
    private Post.AuthorType authorType;
    private String content;
}
