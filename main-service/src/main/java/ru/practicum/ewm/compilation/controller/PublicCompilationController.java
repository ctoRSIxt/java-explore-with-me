package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.PublicCompilationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> find(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                @RequestParam(defaultValue = "10") @Positive Integer size) {
        return publicCompilationService.find(pinned, from, size);
    }


    @GetMapping("/{compId}")
    public CompilationDto findById(@PathVariable Long compId) {
        return publicCompilationService.findById(compId);
    }
}
