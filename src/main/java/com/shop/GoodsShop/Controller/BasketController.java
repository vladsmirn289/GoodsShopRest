package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

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
    public String showBasketItems(@AuthenticationPrincipal Client client,
                                  @CookieValue("jwtToken") String token,
                                  Model model) {
        logger.info("Called showBasketItems method");
        List<ClientItem> basket = clientService.findBasketItemsByClientId(client.getId(), token);

        model.addAttribute("basketItems", basket);

        return "basket/basket";
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItemFromBasket(@PathVariable("itemId") Long clientItemId,
                                       @AuthenticationPrincipal Client client,
                                       @CookieValue("jwtToken") String token) {
        logger.info("Called deleteItemFromBasket method");
        ClientItem itemToDelete = clientItemService.findById(client.getId(), clientItemId, token);
        clientService.deleteBasketItems(Collections.singleton(itemToDelete), client.getId(), token);

        return "redirect:/basket";
    }

    @GetMapping("/delete")
    public String deleteAllItemsFromBasket(@AuthenticationPrincipal Client client,
                                           @CookieValue("jwtToken") String token) {
        logger.info("Called deleteAllItemsFromBasket method");
        clientService.clearBasket(client.getId(), token);

        return "redirect:/basket";
    }
}
