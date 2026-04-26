package com.example.demo.mapper;

import com.example.demo.dto.account.AccountCreateDto;
import com.example.demo.dto.account.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Account toEntity (AccountCreateDto accountCreateDto, User user);

    AccountResponseDto toDto (Account account);
}
