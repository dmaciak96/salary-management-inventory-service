package com.salary.management.inventory_service.controller;

import com.salary.management.inventory_service.exception.BalanceGroupMemberNotFoundException;
import com.salary.management.inventory_service.exception.BalanceGroupNotFoundException;
import com.salary.management.inventory_service.exception.ExpenseNotFoundException;
import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.service.BalanceGroupMemberService;
import com.salary.management.inventory_service.service.BalanceGroupService;
import com.salary.management.inventory_service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BalanceGroupController {

    private final ExpenseService expenseService;
    private final BalanceGroupService balanceGroupService;
    private final BalanceGroupMemberService balanceGroupMemberService;

    @MutationMapping
    public Mono<BalanceGroupDto> saveBalanceGroup(@Argument BalanceGroupDto balanceGroupDto) {
        return balanceGroupService.save(balanceGroupDto);
    }

    @QueryMapping
    public Flux<BalanceGroupDto> findAllBalanceGroups() {
        return balanceGroupService.findAll();
    }

    @QueryMapping
    public Mono<BalanceGroupDto> findBalanceGroupById(@Argument UUID balanceGroupId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)));
    }

    @MutationMapping
    public Mono<Void> deleteBalanceGroup(@Argument UUID balanceGroupId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMap(balanceGroupDto -> balanceGroupService.delete(balanceGroupId));
    }

    @QueryMapping
    public Flux<ExpenseDto> findAllExpensesFromBalanceGroup(@Argument UUID balanceGroupId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(balanceGroupId));
    }

    @QueryMapping
    public Mono<ExpenseDto> findSingleExpenseInBalanceGroup(@Argument UUID balanceGroupId, @Argument UUID expenseId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(balanceGroupId))
                .filter(expenseDto -> expenseDto.getId().equals(expenseId))
                .switchIfEmpty(Mono.error(new ExpenseNotFoundException(expenseId)))
                .next();
    }

    @MutationMapping
    public Mono<ExpenseDto> saveExpenseInBalanceGroup(@Argument UUID balanceGroupId, @Argument ExpenseDto expense) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMap(balanceGroupDto -> expenseService.save(expense, balanceGroupDto));
    }

    @MutationMapping
    public Mono<Void> deleteExpenseFromBalanceGroup(@Argument UUID balanceGroupId, @Argument UUID expenseId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(balanceGroupId))
                .filter(expenseDto -> expenseDto.getId().equals(expenseId))
                .switchIfEmpty(Mono.error(new ExpenseNotFoundException(expenseId)))
                .next()
                .flatMap(expenseDto -> expenseService.delete(expenseId));
    }

    @QueryMapping
    public Flux<BalanceGroupMemberDto> findAllGroupMembersFromBalanceGroup(@Argument UUID balanceGroupId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(balanceGroupId));
    }

    @QueryMapping
    public Mono<BalanceGroupMemberDto> findSingleGroupMemberInBalanceGroup(@Argument UUID balanceGroupId,
                                                                           @Argument UUID balanceGroupMemberId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(balanceGroupId))
                .filter(balanceGroupMemberDto -> balanceGroupMemberDto.getId().equals(balanceGroupMemberId))
                .switchIfEmpty(Mono.error(
                        new BalanceGroupNotFoundException(balanceGroupId)))
                .next();
    }

    @MutationMapping
    public Mono<BalanceGroupMemberDto> saveGroupMemberInBalanceGroup(@Argument UUID balanceGroupId,
                                                                     @Argument BalanceGroupMemberDto balanceGroupMember) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMap(balanceGroupDto -> balanceGroupMemberService.save(balanceGroupMember, balanceGroupDto));
    }

    @MutationMapping
    public Mono<Void> deleteGroupMemberFromBalanceGroup(@Argument UUID balanceGroupId,
                                                        @Argument UUID balanceGroupMemberId) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(new BalanceGroupNotFoundException(balanceGroupId)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(balanceGroupId))
                .filter(balanceGroupMemberDto -> balanceGroupMemberDto.getId().equals(balanceGroupMemberId))
                .switchIfEmpty(Mono.error(new BalanceGroupMemberNotFoundException(balanceGroupMemberId)))
                .next()
                .flatMap(balanceGroupMemberDto -> balanceGroupMemberService.delete(balanceGroupMemberId));
    }
}
