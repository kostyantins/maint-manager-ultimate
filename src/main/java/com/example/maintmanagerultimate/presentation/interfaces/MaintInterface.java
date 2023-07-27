package com.example.maintmanagerultimate.presentation.interfaces;

import com.example.maintmanagerultimate.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MaintInterface {

    @Operation(description = "Create Maint, returns created Maint id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CreateMaintResponseDto"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<CreateMaintResponseDto> createMaint(
            @RequestBody(description = "Create Maint request body object", required = true)
                    CreateMaintRequestDto maint
    );

    @Operation(description = "Get Maint by Maint id, returns Maint object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintResponseDto.class"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetMaintResponseDto> getMaint(
            @Parameter(description = "Maint id path variable", required = true)
                    Long maintId
    );

    @Operation(description = "Get all Maints, returns an array of Maint objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List<GetMaintResponseDto.class>"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<GetMaintResponseDto>> getMaints();

    @Operation(description = "Get Maint, returns Maint object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintResponseDto.class"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetMaintResponseDto> getMaintByIdIdentifier(
            @Parameter(description = "Maint id identifier request parameter", required = true)
                    String maintIdentifier
    );

    @Operation(description = "Delete Maint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<HttpStatus> deleteMaint(
            @Parameter(description = "Maint id path variable", required = true)
                    Long maintId
    );

    @Operation(description = "Patch Maint fix version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<HttpStatus> patchMaintFixVersion(
            @RequestBody(description = "Patch Maint request body object", required = true)
                    FixVersionRequestDto fixVersionRequestDto
    );

    @Operation(description = "Update Maint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<HttpStatus> updateMaint(
            @RequestBody(description = "Update Maint request body object", required = true)
                    UpdateMaintDto updateMaintDto
    );
}
