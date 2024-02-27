package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {

    CategoryDTO getCategory(int categoryId);
    List<CategoryDTO> getCategories();

    void insertCategory(String accountId, CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(int categoryId);
}
