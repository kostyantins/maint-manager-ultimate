package com.example.maintmanagerultimate.presenttation.swagger;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentsRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MaintCommentsSwagger {

    @Operation(description = "Create Maint Comment, returns created Maint Comment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CreateMaintCommentResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<?> createComment(
            @RequestBody(description = "Create Maint Comment", required = true)
                    CreateMaintCommentsRequestDto maintComment
    );

    @Operation(description = "Get Maint Comment by Comment id, returns Maint Comment object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintCommentsResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetMaintCommentsResponseDto> getComment(
            @Parameter(description = "Maint Comment id request parameter", required = true)
                    Long maintCommentId
    );

    @Operation(description = "Get Maint Comment, returns Maint Comment object (pageable)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintCommentsResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<Page<GetMaintCommentsResponseDto>> getMaintCommentsPageable(
            @Parameter(description = "Maint Comment id request parameter", required = true)
            Pageable pageable
    );

    @Operation(description = "Get all identifier Maint Comments, returns an array of identifier Maint Comment objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MaintCommentsMaintIdentifierDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<MaintCommentsMaintIdentifierDto>> getIdentifiedMaintComments();

    @Operation(description = "Get all Maint Comments, returns an array of Maint Comment objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintCommentsResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<GetMaintCommentsResponseDto>> getComments();

    @Operation(description = "Get all Maint Comments, returns an array of Maint Comment objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintCommentsResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<GetMaintCommentsResponseDto>> getAllComments();

    @Operation(description = "Delete Maint Comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<HttpStatus> deleteComment(
            @Parameter(description = "Maint Comment id path variable", required = true)
                    Long commentId);
}
