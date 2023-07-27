package com.example.maintmanagerultimate.presentation.interfaces;

import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MaintProfileInterface {

    @Operation(description = "Create Maint profile, returns created Maint profile id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CreateMaintProfileResponseDto"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<CreateMaintProfileResponseDto> createMaintProfile(
            @RequestBody(description = "Create Maint profile request body object", required = true)
                    CreateMaintProfileRequestDto maintProfile
    );

    @Operation(description = "Get Maint profile by id, returns Maint profile object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetMaintProfileResponseDto.class"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetMaintProfileResponseDto> getMaint(
            @Parameter(description = "Maint prifile id path variable", required = true)
                    Long maintProfileId
    );

    @Operation(description = "Delete Maint profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<HttpStatus> deleteMaintProfile(
            @Parameter(description = "Maint profiel id path variable", required = true)
                    Long maintProfileId
    );
}
