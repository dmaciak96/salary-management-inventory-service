package com.salary.management.inventory_service.repository;

import com.salary.management.inventory_service.model.entity.BalanceGroupMember;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface BalanceGroupMemberRepository extends ReactiveMongoRepository<BalanceGroupMember, UUID> {
    Flux<BalanceGroupMember> findAllByBalanceGroupId(UUID balanceGroupId);
}
