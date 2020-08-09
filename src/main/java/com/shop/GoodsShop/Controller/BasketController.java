package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping("/basket")
@PreAuthorize(value = "isAuthenticated()")
public class BasketController {
    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private ClientService clientService;
    private ClientItemService clientItemService;

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setClientItemService(ClientItemService clientItemService) {
        logger.debug("Setting clientItemService");
        this.clientItemService = clientItemService;
    }

    @GetMapping
    @Transactional
    public String showBasketItems(@AuthenticationPrincipal Client client,
                                Model model) {
        logger.info("Called showBasketItems method");
        Client persistentClient = clientService.findByLogin(client.getLogin());
        logger.info("Initializing lazy getBasket...");
        Hibernate.initialize(persistentClient.getBasket());
        logger.info("Successful init");

        model.addAttribute("basketItems", persistentClient.getBasket());

        return "basket/basket";
    }

    @GetMapping("/delete/{itemId}")
    @Transactional
    public String deleteItemFromBasket(@PathVariable("itemId") Long clientItemId,
                                       @AuthenticationPrincipal Client client) {
        logger.info("Called deleteItemFromBasket method");
        Client persistentClient = clientService.findByLogin(client.getLogin());
        ClientItem itemToDelete = clientItemService.findById(clientItemId);
        persistentClient.getBasket().remove(itemToDelete);
        clientService.deleteBasketItems(Collections.singleton(itemToDelete), client.getId());

        clientService.save(persistentClient);

        return "redirect:/basket";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteAllItemsFromBasket(@AuthenticationPrincipal Client client) {
        logger.info("Called deleteAllItemsFromBasket method");
        Client persistentClient = clientService.findByLogin(client.getLogin());
        Set<ClientItem> clientItemSet = persistentClient.getBasket();
        clientService.deleteBasketItems(clientItemSet, client.getId());
        clientItemSet.clear();

        clientService.save(persistentClient);

        return "redirect:/basket";
    }
}
