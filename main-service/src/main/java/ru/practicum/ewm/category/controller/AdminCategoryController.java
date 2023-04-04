package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public CategoryDto create(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return adminCategoryService.create(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        adminCategoryService.delete(catId);
    }

    @PatchMapping("/{catId")
    public CategoryDto update(@PathVariable Long catId,
                              @RequestBody @Valid NewCategoryDto newCategoryDto) {
        return adminCategoryService.update(catId, newCategoryDto);
    }
}