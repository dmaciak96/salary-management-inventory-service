package com.salary.management.inventory_service.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.UUID;

@Data
@Document
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BalanceGroupMember extends AbstractDocument {

    @NotNull
    private UUID userId;

    private String nickname;

    @NotNull
    @DocumentReference(lazy = true)
    private BalanceGroup balanceGroup;
}
