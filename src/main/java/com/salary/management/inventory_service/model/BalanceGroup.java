package com.salary.management.inventory_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Document
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BalanceGroup extends AbstractDocument {

    @NotBlank
    private String name;

    @NotNull
    private UUID ownerUserId;

    @NotNull
    @Builder.Default
    @ReadOnlyProperty
    @DocumentReference(lookup = "{'balanceGroup':?#{#self._id} }")
    private Set<BalanceGroupMember> groupMembers = new HashSet<>();

    @NotNull
    @Builder.Default
    @ReadOnlyProperty
    @DocumentReference(lookup = "{'balanceGroup':?#{#self._id} }")
    private Set<Expense> expenses = new HashSet<>();
}
