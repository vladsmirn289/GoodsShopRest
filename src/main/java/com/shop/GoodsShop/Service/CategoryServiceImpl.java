package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoCategoryException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Repositories.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        logger.info("findByParent method called");
        return categoryRepo.findByParent(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByParentIsNull() {
        logger.info("findByParentIsNull method called");
        return categoryRepo.findByParentIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        logger.info("findById method called for category with id = " + id);
        return categoryRepo.findById(id).orElseThrow(NoCategoryException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(String name) {
        logger.info("findByName method called");
        return categoryRepo.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getAllNamesOfCategories() {
        logger.info("getAllNamesOfCategories method called");
        return categoryRepo.findByParentIsNull().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getAllNamesOfChildren() {
        logger.info("getAllNamesOfChildren method called");
        return categoryRepo.findByParentIsNull().parallelStream()
                .map(categoryRepo::findByParent)
                .flatMap(Collection::stream)
                .map(Category::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void save(Category category) {
        logger.info("Saving category to database");
        categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        logger.info("Deleting category with id = " + category.getId() + " from database");
        categoryRepo.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting category with id = " + id + " from database");
        categoryRepo.deleteById(id);
    }
}
