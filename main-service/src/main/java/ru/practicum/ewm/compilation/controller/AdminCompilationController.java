package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.compilation.service.AdminCompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return adminCompilationService.create(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        adminCompilationService.delete(compId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable Long compId,
                                 @RequestBody @Valid UpdateCompilationDto newCompilationDto) {
        return adminCompilationService.update(compId, newCompilationDto);
    }
}
