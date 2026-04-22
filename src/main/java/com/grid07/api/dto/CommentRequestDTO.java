package com.grid07.api.dto;

import com.grid07.api.entity.Post;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {



    @NonNull
    private Long authorId;

    @NotNull
    private Post.AuthorType authorType;

    @NotBlank
    private String content;

    @NotBlank
    private int depthLevel;
}
