package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ExpenseService {

    Mono<ExpenseDto> save(ExpenseDto dto, BalanceGroupDto balanceGroupDto);

    Mono<Void> delete(UUID id);

    Mono<ExpenseDto> findById(UUID id);

    Flux<ExpenseDto> findAll();
}
