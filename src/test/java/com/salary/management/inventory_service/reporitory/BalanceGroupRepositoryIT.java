package com.salary.management.inventory_service.reporitory;

import com.salary.management.inventory_service.AbstractIT;
import com.salary.management.inventory_service.model.BalanceGroup;
import com.salary.management.inventory_service.repository.BalanceGroupRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BalanceGroupRepositoryIT extends AbstractIT {
    private static final UUID OWNER_ID = UUID.randomUUID();
    private static final String NAME = "Test Name";

    private final BalanceGroupRepository balanceGroupRepository;

    @Autowired
    public BalanceGroupRepositoryIT(BalanceGroupRepository balanceGroupRepository) {
        this.balanceGroupRepository = balanceGroupRepository;
    }

    @Test
    void shouldSaveProperBalanceGroup() {
        var balanceGroup = createBalanceGroup();

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .assertNext(savedBalanceGroup -> {
                    assertNotNull(savedBalanceGroup.getId());
                    assertNotNull(savedBalanceGroup.getCreatedAt());
                    assertNotNull(savedBalanceGroup.getUpdatedAt());
                    assertEquals(OWNER_ID, savedBalanceGroup.getOwnerUserId());
                    assertEquals(NAME, savedBalanceGroup.getName());
                    assertTrue(savedBalanceGroup.getExpenses().isEmpty());
                    assertTrue(savedBalanceGroup.getGroupMembers().isEmpty());
                })
                .verifyComplete();
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        var balanceGroup = createBalanceGroup();
        balanceGroup.setName(null);

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        var balanceGroup = createBalanceGroup();
        balanceGroup.setName(StringUtils.EMPTY);

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenOwnerUserIdIsNull() {
        var balanceGroup = createBalanceGroup();
        balanceGroup.setOwnerUserId(null);

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenMembersIsNull() {
        var balanceGroup = createBalanceGroup();
        balanceGroup.setGroupMembers(null);

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenExpensesIsNull() {
        var balanceGroup = createBalanceGroup();
        balanceGroup.setExpenses(null);

        StepVerifier.create(balanceGroupRepository.save(balanceGroup))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    private BalanceGroup createBalanceGroup() {
        return BalanceGroup.builder()
                .name(NAME)
                .ownerUserId(OWNER_ID)
                .build();
    }
}
