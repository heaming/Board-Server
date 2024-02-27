package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CategoryMapper {

    public CategoryDTO getCategory(int categoryId);
    public List<CategoryDTO> getCategories();

    public int insertCategory(CategoryDTO categoryDTO);
    public void updateCategory(CategoryDTO categoryDTO);
    public void deleteCategory(int categoryId);
}
