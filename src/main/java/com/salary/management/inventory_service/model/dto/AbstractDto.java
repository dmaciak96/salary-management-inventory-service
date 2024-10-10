package com.salary.management.inventory_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class AbstractDto {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
}
