package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BalanceGroupService {

    Mono<BalanceGroupDto> save(BalanceGroupDto dto);

    Mono<Void> delete(UUID id);

    Mono<BalanceGroupDto> findById(UUID id);

    Flux<BalanceGroupDto> findAll();
}
