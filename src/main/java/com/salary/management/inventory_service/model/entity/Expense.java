package com.salary.management.inventory_service.model.entity;

import com.salary.management.inventory_service.model.SplitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;

@Data
@Document
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Expense extends AbstractDocument {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @DocumentReference
    private BalanceGroupMember paidByGroupMember;

    @DocumentReference
    private BalanceGroupMember needToPayGroupMember;

    @NotNull
    private SplitType splitType;

    private boolean resolved;

    @NotNull
    @DocumentReference(lazy = true)
    private BalanceGroup balanceGroup;
}
