package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ItemService;
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
    private CategoryService categoryService;
    private ItemService itemService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public String categoryItems(@PathVariable("id") Long id,
                                Model model) {
        //TODO: add controller advice for exceptions
        Category category = categoryService.findById(id);
        List<Item> items = itemService.findByCategory(category);

        model.addAttribute("items", items);

        return "categoryItems";
    }
}
