package school.thoughtworks.pos.mapper;


import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface CategoryMapper {
    List<Category> getAllCategories();
    List<Category> getCategoryById(Integer id);

    Integer insertCategory(Category category);

    Integer updateCategoryById(Category category);

    Integer deleteCategoryById(Integer id);

 }
