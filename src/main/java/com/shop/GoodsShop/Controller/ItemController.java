package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.URIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;
    private ClientService clientService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setItemService(ItemService itemService) {
        logger.debug("Setting itemService");
        this.itemService = itemService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @GetMapping
    public String searchItems(@RequestParam("keyword") String keyword,
                              Model model) {
        List<Item> items = itemService.findBySearch(keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("items", items);

        return "categoryItems";
    }

    @GetMapping("{id}/image")
    @ResponseBody
    public byte[] downloadImage(@PathVariable("id") Long id) {
        logger.info("downloadPhoto method called");
        Item item = itemService.findById(id);

        if (item.getImage() != null) {
            logger.info("Download image for item with id = " + id);
        } else {
            logger.warn("Image is null");
        }

        return item.getImage();
    }

    @GetMapping("/{id}")
    public String itemPage(@PathVariable("id") Long id,
                           Model model) {
        logger.info("Called itemPage method");
        Item item = itemService.findById(id);
        model.addAttribute("item", item);

        return "itemPage";
    }

    @PostMapping("/{itemId}/addToBasket")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String addItemToBasket(@RequestParam("quantity") int quantity,
                                  @PathVariable("itemId") Long itemId,
                                  @AuthenticationPrincipal Client client,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer) {
        logger.info("Called addItemToBasket method");
        Item item = itemService.findById(itemId);
        Client persistentClient = entityManager.merge(client);
        ClientItem itemToBasket = new ClientItem(item, quantity);

        persistentClient.getBasket().add(itemToBasket);
        clientService.save(client);

        return "redirect:" + URIUtils.toSamePage(referer, redirectAttributes);
    }
}
