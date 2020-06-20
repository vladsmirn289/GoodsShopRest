package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;
    private ItemService itemService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        logger.debug("Setting categoryService");
        this.categoryService = categoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        logger.debug("Setting itemService");
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public String showCategoryItems(@PathVariable("id") Long id,
                                Model model) {
        logger.info("showCategoryItems method called for category with id = " + id);
        Category category = categoryService.findById(id);
        List<Item> items = itemService.findByCategory(category);

        model.addAttribute("items", items);

        return "categoryItems";
    }
}
