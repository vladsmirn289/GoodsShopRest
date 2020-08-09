package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.URIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;
    private ClientService clientService;
    private ClientItemService clientItemService;

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

    @Autowired
    public void setClientItemService(ClientItemService clientItemService) {
        this.clientItemService = clientItemService;
    }

    @GetMapping
    public String searchItems(@RequestParam("keyword") String keyword,
                              Model model,
                              @PageableDefault(sort = {"name"}) Pageable pageable) {
        logger.info("Called searchItems method");
        Page<Item> items = itemService.findBySearch(keyword, pageable);
        model.addAttribute("url", "/item?keyword=" + keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("items", items);

        return "item/categoryItems";
    }

    @GetMapping("/{id}")
    public String itemPage(@PathVariable("id") Long id,
                           Model model) {
        logger.info("Called itemPage method");
        Item item = itemService.findById(id);
        model.addAttribute("item", item);

        return "item/itemPage";
    }

    @PostMapping("/{itemId}/addToBasket")
    @PreAuthorize("isAuthenticated()")
    public String addItemToBasket(@RequestParam("quantity") int quantity,
                                  @PathVariable("itemId") Long itemId,
                                  @AuthenticationPrincipal Client client,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer,
                                  @CookieValue("jwtToken") String token) {
        logger.info("Called addItemToBasket method");
        Item item = itemService.findById(itemId);
        ClientItem itemToBasket = new ClientItem(item, quantity);
        clientItemService.save(itemToBasket, client.getId(), token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }
}
