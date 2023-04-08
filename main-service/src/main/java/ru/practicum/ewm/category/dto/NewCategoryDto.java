package ru.practicum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotNull(message = "Category name cannot be null")
    private String name;
}
