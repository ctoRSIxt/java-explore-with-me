package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        validateCategoryIsUnique(newCategoryDto.getName());
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));

    }

    @Override
    public void delete(Long catId) {

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Category with id=" + catId + " was not found"));

        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Category " + category.getName() + " has connected events and " +
                            "cannot be deleted");
        }

        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto update(Long catId, NewCategoryDto newCategoryDto) {
        validateCategoryIsUnique(newCategoryDto.getName());

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Category with id=" + catId + " was not found"));

        category.setName(newCategoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    private void validateCategoryIsUnique(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Category name " + name + " is not unique (already present)");
        }
    }


}
