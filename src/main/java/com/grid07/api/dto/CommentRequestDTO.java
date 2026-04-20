package com.grid07.api.dto;

import com.grid07.api.entity.Post;
import lombok.Data;

@Data
public class CommentRequestDTO {

    private Long authorId;
    private Post.AuthorType authorType;
    private String content;
    private int depthLevel;
}
