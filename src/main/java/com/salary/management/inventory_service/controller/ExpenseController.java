package com.salary.management.inventory_service.controller;

import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.service.BalanceGroupMemberService;
import com.salary.management.inventory_service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BalanceGroupMemberService balanceGroupMemberService;

    @GetMapping("/expenses")
    public Flux<ExpenseDto> findAll() {
        return expenseService.findAll();
    }

    @GetMapping("/members")
    public Flux<BalanceGroupMemberDto> findAllMembers() {
        return balanceGroupMemberService.findAll();
    }
}
