package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategory(int categoryId) {
        CategoryDTO categoryDTO = categoryMapper.getCategory(categoryId);

        if(categoryDTO != null) {
            return categoryDTO;
        } else {
            log.error("getCategory Error: {}", categoryId);
            throw new RuntimeException("categoryId를 확인해주세요");
        }
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return categoryMapper.getCategories();
    }

    @Override
    public void insertCategory(String accountId, CategoryDTO categoryDTO) {
        if(accountId != null) {
            categoryMapper.insertCategory(categoryDTO);
        } else {
            log.error("insertCategory Error: {}", categoryDTO);
            throw new RuntimeException("insert error! 게시글 카테고리 등록 메서드를 확인하세요!");
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        if(categoryDTO != null) {
            categoryMapper.updateCategory(categoryDTO);
        } else {
            log.error("updateCategory Error: {}", categoryDTO);
            throw new RuntimeException("update error! 게시글 카테고리 수정 메서드를 확인하세요!");
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        if(categoryId != 0) {
            categoryMapper.deleteCategory(categoryId);
        } else {
            log.error("deleteCatetory Error: {} ", categoryId);
            throw new RuntimeException("delete error! 게시글 카테고리 삭제 메서드를 확인하세요!");
        }
    }
}
