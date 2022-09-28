package com.example.maintmanagerultimate.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintCommentResponseDto {

    private Long maintCommentId;
}
