package com.salary.management.inventory_service.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BalanceGroupDto extends AbstractDto {

    private String name;
    private UUID ownerUserId;

    @Builder.Default
    private Set<BalanceGroupMemberDto> groupMembers = new HashSet<>();

    @Builder.Default
    private Set<ExpenseDto> expenses = new HashSet<>();
}
