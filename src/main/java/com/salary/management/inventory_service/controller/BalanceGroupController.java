package com.salary.management.inventory_service.controller;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.model.http.ExpenseHttpRequest;
import com.salary.management.inventory_service.service.BalanceGroupMemberService;
import com.salary.management.inventory_service.service.BalanceGroupService;
import com.salary.management.inventory_service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/balance-groups")
@RequiredArgsConstructor
public class BalanceGroupController {

    private final ExpenseService expenseService;
    private final BalanceGroupService balanceGroupService;
    private final BalanceGroupMemberService balanceGroupMemberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BalanceGroupDto> saveBalanceGroup(@RequestBody BalanceGroupDto balanceGroupDto) {
        return balanceGroupService.save(balanceGroupDto);
    }

    @GetMapping
    public Flux<BalanceGroupDto> findAllBalanceGroups() {
        return balanceGroupService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<BalanceGroupDto> findBalanceGroupById(@PathVariable UUID id) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBalanceGroup(@PathVariable UUID id) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMap(balanceGroupDto -> balanceGroupService.delete(id));
    }

    @GetMapping("/{id}/expenses")
    public Flux<ExpenseDto> findAllExpensesFromBalanceGroup(@PathVariable UUID id) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(id));
    }

    @GetMapping("/{id}/expenses/{expenseId}")
    public Mono<ExpenseDto> findSingleExpenseInBalanceGroup(@PathVariable UUID id, @PathVariable UUID expenseId) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(id))
                .filter(expenseDto -> expenseDto.getId().equals(expenseId))
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with id: " + expenseId)))
                .next();
    }

    @PostMapping("/{id}/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ExpenseDto> saveExpenseInBalanceGroup(@PathVariable UUID id, @RequestBody ExpenseHttpRequest expenseRequest) {
        if (expenseRequest.needToPayGroupMember() == null) {
            return saveWithoutNeedToPay(id, expenseRequest);
        } else {
            return saveWithNeedToPay(id, expenseRequest);
        }
    }

    private Mono<ExpenseDto> saveWithoutNeedToPay(UUID balanceGroupId, ExpenseHttpRequest expenseRequest) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + balanceGroupId)))
                .flatMap(balanceGroupDto -> {
                    var paidByGroupMemberMono = findGroupMemberById(balanceGroupId, expenseRequest.paidByGroupMember());
                    return Mono.zip(paidByGroupMemberMono, Mono.just(balanceGroupDto));
                })
                .flatMap(tuple -> {
                    var paidByGroupMember = tuple.getT1();
                    var balanceGroupDto = tuple.getT2();
                    return mapToDtoAndSave(expenseRequest, balanceGroupDto, paidByGroupMember, null);
                });
    }

    private Mono<ExpenseDto> saveWithNeedToPay(UUID balanceGroupId, ExpenseHttpRequest expenseRequest) {
        return balanceGroupService.findById(balanceGroupId)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + balanceGroupId)))
                .flatMap(balanceGroupDto -> {
                    var paidByGroupMemberMono = findGroupMemberById(balanceGroupId, expenseRequest.paidByGroupMember());
                    var needToPayGroupMemberMono = findGroupMemberById(balanceGroupId, expenseRequest.needToPayGroupMember());
                    return Mono.zip(needToPayGroupMemberMono, paidByGroupMemberMono, Mono.just(balanceGroupDto));
                })
                .flatMap(tuple -> {
                    var needToPayGroupMember = tuple.getT1();
                    var paidByGroupMember = tuple.getT2();
                    var balanceGroupDto = tuple.getT3();
                    return mapToDtoAndSave(expenseRequest, balanceGroupDto, paidByGroupMember, needToPayGroupMember);
                });
    }

    private Mono<BalanceGroupMemberDto> findGroupMemberById(UUID balanceGroupId, UUID memberId) {
        return balanceGroupMemberService.findAllByBalanceGroupId(balanceGroupId)
                        .filter(balanceGroupMember -> balanceGroupMember.getId().equals(memberId))
                        .next();
    }

    private Mono<ExpenseDto> mapToDtoAndSave(ExpenseHttpRequest expenseRequest,
                                             BalanceGroupDto balanceGroupDto,
                                             BalanceGroupMemberDto paidByGroupMember,
                                             BalanceGroupMemberDto needToPayGroupMember) {
        if (paidByGroupMember == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paid By Group Member not found with id: " + expenseRequest.paidByGroupMember());
        }
        var expenseDto = ExpenseDto.builder()
                .name(expenseRequest.name())
                .amount(expenseRequest.amount())
                .paidByGroupMember(paidByGroupMember)
                .needToPayGroupMember(needToPayGroupMember)
                .splitType(expenseRequest.splitType())
                .resolved(expenseRequest.resolved())
                .build();
        return expenseService.save(expenseDto, balanceGroupDto);
    }

    @DeleteMapping("/{id}/expenses/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteExpenseFromBalanceGroup(@PathVariable UUID id, @PathVariable UUID expenseId) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> expenseService.findAllByBalanceGroup(id))
                .filter(expenseDto -> expenseDto.getId().equals(expenseId))
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with id: " + expenseId)))
                .next()
                .flatMap(expenseDto -> expenseService.delete(expenseId));
    }

    @GetMapping("/{id}/members")
    public Flux<BalanceGroupMemberDto> findAllGroupMembersFromBalanceGroup(@PathVariable UUID id) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(id));
    }

    @GetMapping("/{id}/members/{memberId}")
    public Mono<BalanceGroupMemberDto> findSingleGroupMemberInBalanceGroup(@PathVariable UUID id, @PathVariable UUID memberId) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(id))
                .filter(balanceGroupMemberDto -> balanceGroupMemberDto.getId().equals(memberId))
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "GroupMember not found with id: " + memberId)))
                .next();
    }

    @PostMapping("/{id}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BalanceGroupMemberDto> saveGroupMemberInBalanceGroup(@PathVariable UUID id,
                                                                     @RequestBody BalanceGroupMemberDto balanceGroupMemberDto) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMap(balanceGroupDto -> balanceGroupMemberService.save(balanceGroupMemberDto, balanceGroupDto));
    }

    @DeleteMapping("/{id}/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGroupMemberFromBalanceGroup(@PathVariable UUID id, @PathVariable UUID memberId) {
        return balanceGroupService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "BalanceGroup not found with id: " + id)))
                .flatMapMany(balanceGroupDto -> balanceGroupMemberService.findAllByBalanceGroupId(id))
                .filter(balanceGroupMemberDto -> balanceGroupMemberDto.getId().equals(memberId))
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "GroupMember not found with id: " + memberId)))
                .next()
                .flatMap(balanceGroupMemberDto -> balanceGroupMemberService.delete(memberId));
    }
}
