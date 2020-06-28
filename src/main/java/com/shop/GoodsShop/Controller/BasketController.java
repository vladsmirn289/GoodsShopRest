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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

@Controller
@RequestMapping("/basket")
@PreAuthorize(value = "isAuthenticated()")
public class BasketController {
    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);
    private ClientService clientService;
    private ClientItemService clientItemService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setClientItemService(ClientItemService clientItemService) {
        this.clientItemService = clientItemService;
    }

    @GetMapping
    @Transactional
    public String showBasketItems(@AuthenticationPrincipal Client client,
                                Model model) {
        Client persistentClient = entityManager.merge(client);
        Hibernate.initialize(persistentClient.getBasket());

        model.addAttribute("basketItems", persistentClient.getBasket());

        return "basket";
    }

    @GetMapping("/delete/{itemId}")
    @Transactional
    public String deleteItemFromBasket(@PathVariable("itemId") Long clientItemId,
                                       @AuthenticationPrincipal Client client,
                                       RedirectAttributes redirectAttributes,
                                       @RequestHeader(required = false) String referer) {
        Client persistentClient = entityManager.merge(client);
        ClientItem itemToDelete = clientItemService.findById(clientItemId);
        persistentClient.getBasket().remove(itemToDelete);

        clientService.save(persistentClient);

        return "redirect:/basket";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteAllItemsFromBasket(@AuthenticationPrincipal Client client,
                                           RedirectAttributes redirectAttributes,
                                           @RequestHeader(required = false) String referer) {
        Client persistentClient = entityManager.merge(client);
        persistentClient.getBasket().clear();
        clientService.save(client);

        return "redirect:/basket";
    }

    @GetMapping("/checkout")
    @Transactional
    public String checkoutAllItems(@AuthenticationPrincipal Client client,
                                   Model model) {
        Client persistentClient = entityManager.merge(client);

        model.addAttribute("clientItems", persistentClient.getBasket());
        model.addAttribute("client", client);

        return "checkoutPage";
    }

    @GetMapping("/checkout/{itemId}")
    @Transactional
    public String checkoutAllItems(@AuthenticationPrincipal Client client,
                                   @PathVariable("itemId") Long id,
                                   Model model) {
        Client persistentClient = entityManager.merge(client);
        ClientItem item = clientItemService.findById(id);

        model.addAttribute("clientItems", Collections.singletonList(item));
        model.addAttribute("client", persistentClient);

        return "checkoutPage";
    }
}
