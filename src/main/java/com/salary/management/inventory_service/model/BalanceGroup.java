package com.salary.management.inventory_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class BalanceGroup {

    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

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
