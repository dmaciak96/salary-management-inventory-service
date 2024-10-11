package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BalanceGroupMemberService {

    Mono<BalanceGroupMemberDto> save(BalanceGroupMemberDto dto, BalanceGroupDto balanceGroupDto);

    Mono<Void> delete(UUID id);

    Mono<BalanceGroupMemberDto> findById(UUID id);

    Flux<BalanceGroupMemberDto> findAll();

    Flux<BalanceGroupMemberDto> findAllByBalanceGroupId(UUID balanceGroupId);
}
