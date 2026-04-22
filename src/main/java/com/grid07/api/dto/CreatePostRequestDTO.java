package com.grid07.api.dto;

import com.grid07.api.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequestDTO {

    @NotNull
    private Long authorId;

    @NotNull
    private Post.AuthorType authorType;

    @NotBlank
    private String content;
}
