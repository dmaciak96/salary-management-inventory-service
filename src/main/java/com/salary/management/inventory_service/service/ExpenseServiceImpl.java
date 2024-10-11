package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.model.mapper.BalanceGroupMapper;
import com.salary.management.inventory_service.model.mapper.ExpenseMapper;
import com.salary.management.inventory_service.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final BalanceGroupMapper balanceGroupMapper;

    @Override
    public Mono<ExpenseDto> save(ExpenseDto dto, BalanceGroupDto balanceGroupDto) {
        var expenseEntity = expenseMapper.toEntity(dto);
        var balanceGroupEntity = balanceGroupMapper.toEntity(balanceGroupDto);
        expenseEntity.setBalanceGroup(balanceGroupEntity);
        return expenseRepository.save(expenseEntity)
                .map(expenseMapper::toDto);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return expenseRepository.deleteById(id);
    }

    @Override
    public Mono<ExpenseDto> findById(UUID id) {
        return expenseRepository.findById(id)
                .map(expenseMapper::toDto);
    }

    @Override
    public Flux<ExpenseDto> findAll() {
        return expenseRepository.findAll()
                .map(expenseMapper::toDto);
    }

    @Override
    public Flux<ExpenseDto> findAllByBalanceGroup(UUID balanceGroupId) {
        return expenseRepository.findAllByBalanceGroupId(balanceGroupId)
                .map(expenseMapper::toDto);
    }
}
