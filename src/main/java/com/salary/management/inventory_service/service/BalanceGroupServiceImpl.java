package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.mapper.BalanceGroupMapper;
import com.salary.management.inventory_service.repository.BalanceGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceGroupServiceImpl implements BalanceGroupService {

    private final BalanceGroupRepository balanceGroupRepository;
    private final BalanceGroupMapper balanceGroupMapper;
    private final ExpenseService expenseService;
    private final BalanceGroupMemberService balanceGroupMemberService;

    @Override
    public Mono<BalanceGroupDto> save(BalanceGroupDto dto) {
        return Mono.just(balanceGroupMapper.toEntity(dto))
                .flatMap(balanceGroupRepository::save)
                .map(balanceGroupMapper::toDto);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return balanceGroupRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(balanceGroup -> deleteExpensesAndGroupMembers(balanceGroupMapper.toDto(balanceGroup)))
                .flatMap(balanceGroupDto -> balanceGroupRepository.deleteById(id));
    }

    private Mono<BalanceGroupDto> deleteExpensesAndGroupMembers(BalanceGroupDto balanceGroupDto) {
        var savedExpensesMono = expenseService.findAllByBalanceGroup(balanceGroupDto.getId())
                .flatMap(expenseDto -> expenseService.delete(expenseDto.getId()))
                .then();

        var savedMembersMono = balanceGroupMemberService.findAllByBalanceGroupId(balanceGroupDto.getId())
                .flatMap(balanceGroupMemberDto -> balanceGroupMemberService.delete(balanceGroupMemberDto.getId()))
                .then();

        return Mono.when(savedExpensesMono, savedMembersMono)
                .thenReturn(balanceGroupDto);
    }

    @Override
    public Mono<BalanceGroupDto> findById(UUID id) {
        return balanceGroupRepository.findById(id)
                .map(balanceGroupMapper::toDto);
    }

    @Override
    public Flux<BalanceGroupDto> findAll() {
        return balanceGroupRepository.findAll()
                .map(balanceGroupMapper::toDto);
    }
}
