package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoCategoryException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Repositories.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepo categoryRepo;

    @Autowired
    public void setCategoryRepo(CategoryRepo categoryRepo) {
        logger.debug("Setting categoryRepo");
        this.categoryRepo = categoryRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByParent(Category parent) {
        logger.info("findByParent method called for parent = " + parent.getName());
        return categoryRepo.findByParent(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByParentIsNull() {
        logger.info("findByParentIsNull method called");
        return categoryRepo.findByParentIsNull();
    }

    @Override
    public Category findById(Long id) {
        logger.info("findById method called for category with id = " + id);
        return categoryRepo.findById(id).orElseThrow(NoCategoryException::new);
    }

    @Override
    public void save(Category category) {
        logger.info("Saving category with name = " + category.getName() + " to database");
        categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        logger.info("Deleting category with name = " + category.getName() + " from database");
        categoryRepo.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting category with id = " + id + " from database");
        categoryRepo.deleteById(id);
    }
}
