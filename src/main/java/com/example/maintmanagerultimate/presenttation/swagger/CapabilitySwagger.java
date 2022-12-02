package com.example.maintmanagerultimate.presenttation.swagger;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.service.dto.CreateCapabilityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CapabilitySwagger {

    @Operation(description = "Create Capability, returns created Capability id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CreateMaintCommentResponseDto.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<CreateCapabilityResponseDpo> createCapability(
            @RequestBody(description = "Create Capability", required = true)
                    Capability capability
    );

    @Operation(description = "Get Capability, returns created Capability object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetCapabilityResponseDto.class"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetCapabilityResponseDto> getCapability(
            @Parameter(description = "Capability id path variable", required = true)
                    Long capabilityId);

    @Operation(description = "Get all Capability, returns an array of Capability objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List<GetCapabilityResponseDto.class>"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<GetCapabilityResponseDto>> getCapabilities();
}
