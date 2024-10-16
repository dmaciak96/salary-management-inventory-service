package com.salary.management.inventory_service.repository;

import com.salary.management.inventory_service.model.entity.BalanceGroup;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BalanceGroupRepository extends ReactiveMongoRepository<BalanceGroup, UUID> {
}
