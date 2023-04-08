package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.AdminUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository userRepository;

    @Override
    public List<UserDto> find(List<Long> ids, Integer from, Integer size) {
        Page<User> usersPage;
        if (ids == null) {
            usersPage = userRepository.findAll(
                    PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")));
        } else {
            usersPage = userRepository.findAllByIdIn(ids,
                    PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")));
        }
        return usersPage.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto create(UserDto user) {
        if (userRepository.findAllByEmail(user.getEmail()).isPresent()) {
            throw new ConditionsNotMetException(
                    "For the requested operation the conditions are not met.",
                    "Email " + user.getEmail() + " is not unique");
        }

        if (userRepository.findAllByName(user.getName()).isPresent()) {
            throw new ConditionsNotMetException(
                    "For the requested operation the conditions are not met.",
                    "Name " + user.getName() + " is not unique");
        }

        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(user)));
    }

    @Transactional
    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
