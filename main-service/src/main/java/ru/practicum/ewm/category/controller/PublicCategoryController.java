package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.PublicCategoryService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicCategoryController {
    private final PublicCategoryService publicCategoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryDto> find(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        return publicCategoryService.find(from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{catId}")
    public CategoryDto findById(@PathVariable Long catId) {
        return publicCategoryService.findById(catId);
    }
}
