package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateMaintCommentsRequestDto {

    private String commentText;
    private LocalDate createdDate;
    private CreateMaintRequestDto maint;
}

