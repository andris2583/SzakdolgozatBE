package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.models.Category;
import com.szte.szakdolgozat.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category insertCategory(Category category){
        return categoryRepository.insert(category);
    }

    public void deleteCategory(Category category){
        categoryRepository.delete(category);
    }

}
