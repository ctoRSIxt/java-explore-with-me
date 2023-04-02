package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;


    @PostMapping
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return adminCompilationService.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        adminCompilationService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @RequestBody NewCompilationDto newCompilationDto) {
        return adminCompilationService.update(compId, newCompilationDto);
    }
}
