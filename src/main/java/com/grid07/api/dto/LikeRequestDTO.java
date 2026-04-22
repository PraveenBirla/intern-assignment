package com.grid07.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeRequestDTO {

    @NotNull
    private Long userId;
}
