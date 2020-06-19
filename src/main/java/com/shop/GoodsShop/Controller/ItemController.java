package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        Item item = itemService.findById(id);

        if (item.getImage() != null) {
            logger.info("Download image for item with id = " + id);
        }

        return item.getImage();
    }
}
