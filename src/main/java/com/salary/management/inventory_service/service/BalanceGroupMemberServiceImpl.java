package com.salary.management.inventory_service.service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.mapper.BalanceGroupMapper;
import com.salary.management.inventory_service.model.mapper.BalanceGroupMemberMapper;
import com.salary.management.inventory_service.repository.BalanceGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceGroupMemberServiceImpl implements BalanceGroupMemberService {

    private final BalanceGroupMemberRepository balanceGroupMemberRepository;
    private final BalanceGroupMemberMapper balanceGroupMemberMapper;
    private final BalanceGroupMapper balanceGroupMapper;

    @Override
    public Mono<BalanceGroupMemberDto> save(BalanceGroupMemberDto dto, BalanceGroupDto balanceGroupDto) {
        var balanceGroupMemberEntity = balanceGroupMemberMapper.toEntity(dto);
        var balanceGroupEntity = balanceGroupMapper.toEntity(balanceGroupDto);
        balanceGroupMemberEntity.setBalanceGroup(balanceGroupEntity);
        return balanceGroupMemberRepository.save(balanceGroupMemberEntity)
                .map(balanceGroupMemberMapper::toDto);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return balanceGroupMemberRepository.deleteById(id);
    }

    @Override
    public Mono<BalanceGroupMemberDto> findById(UUID id) {
        return balanceGroupMemberRepository.findById(id)
                .map(balanceGroupMemberMapper::toDto);
    }

    @Override
    public Flux<BalanceGroupMemberDto> findAll() {
        return balanceGroupMemberRepository.findAll()
                .map(balanceGroupMemberMapper::toDto);
    }
}
