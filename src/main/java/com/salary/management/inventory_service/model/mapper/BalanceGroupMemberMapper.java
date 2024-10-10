package com.salary.management.inventory_service.model.mapper;

import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.entity.BalanceGroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalanceGroupMemberMapper {

    @Mapping(target = "balanceGroup", ignore = true)
    BalanceGroupMember toEntity(BalanceGroupMemberDto dto);

    List<BalanceGroupMember> toEntityList(List<BalanceGroupMemberDto> dtoList);

    BalanceGroupMemberDto toDto(BalanceGroupMember entity);

    List<BalanceGroupMemberDto> toDtoList(List<BalanceGroupMember> entities);
}
