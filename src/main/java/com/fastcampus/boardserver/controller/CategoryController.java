package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    private CategoryService categoryService;

    // Request Example - inner class
    @Getter
    @Setter
    private static class CategoryRequest {
        private int id;
        private String name;
    }

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{categoryId}")
    public CategoryDTO getCateGory(@PathVariable(name="categoryId") int categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/list")
    public List<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void insertCategory(String accountId,
                               @RequestBody CategoryDTO categoryDTO) {
        categoryService.insertCategory(accountId, categoryDTO);
    }

    @PatchMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCatetory(String accountId,
                               @PathVariable(name="categoryId") int categoryId,
                               @RequestBody CategoryRequest categoryRequest) {

        CategoryDTO categoryDTO = new CategoryDTO(categoryId,
                categoryRequest.getName(), CategoryDTO.SortStatus.NEWEST, 10, 1);
        categoryDTO.setId(categoryRequest.getId());
        categoryDTO.setName(categoryRequest.getName());
        categoryDTO.setPagingStartOffset(10);
        categoryService.updateCategory(categoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void deleteCategory(String accountId,
                               @PathVariable(name="categoryId") int categoryId
                               ) {
        categoryService.deleteCategory(categoryId);
    }
}
