package com.salary.management.inventory_service.model.mapper;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.entity.BalanceGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {BalanceGroupMemberMapper.class, ExpenseMapper.class})
public interface BalanceGroupMapper {

    BalanceGroup toEntity(BalanceGroupDto dto);

    List<BalanceGroup> toEntityList(List<BalanceGroupDto> dtoList);

    BalanceGroupDto toDto(BalanceGroup entity);

    List<BalanceGroupDto> toDtoList(List<BalanceGroup> entities);
}
