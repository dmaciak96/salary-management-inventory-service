package com.salary.management.inventory_service.model.mapper;

import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.model.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "balanceGroup", ignore = true)
    Expense toEntity(ExpenseDto dto);

    List<Expense> toEntityList(List<ExpenseDto> dtoList);

    ExpenseDto toDto(Expense entity);

    List<ExpenseDto> toDtoList(List<Expense> entities);
}
