package com.example.maintmanagerultimate.presenttation.swagger;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.service.dto.CreatePriorityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetPriorityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PrioritySwagger {

    @Operation(description = "Create Priorities, returns created Priorities id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CreatePriorityResponseDpo.class"),
            @ApiResponse(responseCode = "400", description = "ResponseErrorDto.class")
    })
    ResponseEntity<CreatePriorityResponseDpo> createPriority(
            @RequestBody(description = "Create Priorities", required = true)
                    Priorities priority);

    @Operation(description = "Get Priorities, returns created Priorities object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetPriorityResponseDto.class"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<GetPriorityResponseDto> getPriority(
            @Parameter(description = "Priorities id path variable", required = true)
                    Long priorityId
    );

    @Operation(description = "Get all Priorities, returns an array of Priorities objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List<GetPriorityResponseDto.class>"),
            @ApiResponse(responseCode = "404", description = "ResponseErrorDto.class")
    })
    ResponseEntity<List<GetPriorityResponseDto>> getPriorities();
}
