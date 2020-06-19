package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        logger.debug("Setting categoryService");
        this.categoryService = categoryService;
    }

    @GetMapping
    public String mainPage(Model model) {
        logger.info("mainPage method called");
        Map<Category, List<Category>> treeOfCategories = new HashMap<>();
        List<Category> parents = categoryService.findByParentIsNull();

        for (Category parent : parents) {
            treeOfCategories.put(parent, categoryService.findByParent(parent));
        }

        model.addAttribute("treeOfCategories", treeOfCategories);

        return "main";
    }
}
