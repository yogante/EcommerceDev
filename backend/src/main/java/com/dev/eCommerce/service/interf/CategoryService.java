package com.dev.eCommerce.service.interf;

import com.dev.eCommerce.dto.CategoryDto;
import com.dev.eCommerce.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}
