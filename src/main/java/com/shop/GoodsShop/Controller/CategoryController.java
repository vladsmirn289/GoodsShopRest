package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        logger.debug("Setting categoryService");
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String showCategoryItems(@PathVariable("id") Long id,
                                    Model model,
                                    @PageableDefault(sort = {"name"}) Pageable pageable) {
        logger.info("showCategoryItems method called for category with id = " + id);
        Category category = categoryService.findById(id);
        Page<Item> items = categoryService.getAllItemsByCategory(category, pageable);

        model.addAttribute("url", "/category/" + id + "?");
        model.addAttribute("items", items);

        return "item/categoryItems";
    }
}
