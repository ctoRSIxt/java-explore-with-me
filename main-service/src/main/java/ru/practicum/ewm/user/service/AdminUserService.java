package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> find(List<Long> ids, Integer from, Integer size);

    UserDto create(UserDto user);

    void delete(Long userId);
}
