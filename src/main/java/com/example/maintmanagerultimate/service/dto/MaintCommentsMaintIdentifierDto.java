package com.example.maintmanagerultimate.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintCommentsMaintIdentifierDto {

    private String maintIdentifier;
    private String maintComments;
}
