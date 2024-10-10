package com.salary.management.inventory_service.reporitory;

import com.salary.management.inventory_service.AbstractIT;
import com.salary.management.inventory_service.model.BalanceGroup;
import com.salary.management.inventory_service.model.BalanceGroupMember;
import com.salary.management.inventory_service.repository.BalanceGroupMemberRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BalanceGroupMemberRepositoryIT extends AbstractIT {
    private static final String NICKNAME = "Test nickname";
    private static final UUID USER_ID = UUID.randomUUID();

    private final BalanceGroupMemberRepository balanceGroupMemberRepository;

    @Autowired
    public BalanceGroupMemberRepositoryIT(BalanceGroupMemberRepository balanceGroupMemberRepository) {
        this.balanceGroupMemberRepository = balanceGroupMemberRepository;
    }

    @Test
    void shouldSaveProperGroupMember() {
        var balanceGroupMember = createBalanceGroupMember();

        StepVerifier.create(balanceGroupMemberRepository.save(balanceGroupMember))
                .assertNext(savedGroupMember -> {
                    assertNotNull(savedGroupMember);
                    assertNotNull(savedGroupMember.getId());
                    assertNotNull(savedGroupMember.getCreatedAt());
                    assertNotNull(savedGroupMember.getUpdatedAt());
                    assertEquals(NICKNAME, savedGroupMember.getNickname());
                    assertEquals(USER_ID, savedGroupMember.getUserId());
                })
                .verifyComplete();
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        var balanceGroupMember = createBalanceGroupMember();
        balanceGroupMember.setUserId(null);

        StepVerifier.create(balanceGroupMemberRepository.save(balanceGroupMember))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenBalanceGroupIsNull() {
        var balanceGroupMember = createBalanceGroupMember();
        balanceGroupMember.setBalanceGroup(null);

        StepVerifier.create(balanceGroupMemberRepository.save(balanceGroupMember))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    private BalanceGroupMember createBalanceGroupMember() {
        return BalanceGroupMember.builder()
                .nickname(NICKNAME)
                .userId(USER_ID)
                .balanceGroup(BalanceGroup.builder().build())
                .build();
    }
}
