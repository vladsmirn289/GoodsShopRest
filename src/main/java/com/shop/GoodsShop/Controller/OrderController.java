package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Order;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.OrderService;
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

@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private ClientService clientService;
    private OrderService orderService;
    private ClientItemService clientItemService;

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        logger.debug("Setting orderService");
        this.orderService = orderService;
    }

    @Autowired
    public void setClientItemService(ClientItemService clientItemService) {
        this.clientItemService = clientItemService;
    }

    @GetMapping
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public String clientOrders(@AuthenticationPrincipal Client client,
                               Model model) {
        logger.info("Called clientOrders method");
        Client persistentClient = clientService.findByLogin(client.getLogin());
        Hibernate.initialize(persistentClient.getOrders());
        model.addAttribute("orders", persistentClient.getOrders());

        return "ordersList";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String concreteClientOrder(@PathVariable("id") Long orderId,
                                      Model model) {
        logger.info("Called concreteClientOrder method");
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);

        return "concreteOrder";
    }

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String checkoutAllItems(@AuthenticationPrincipal Client client,
                                   Model model) {
        logger.info("Called checkoutAllItems method");
        Client persistentClient = clientService.findByLogin(client.getLogin());

        model.addAttribute("clientItems", persistentClient.getBasket());
        model.addAttribute("client", client);

        return "checkoutPage";
    }

    @GetMapping("/checkout/{itemId}")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String checkoutItem(@AuthenticationPrincipal Client client,
                               @PathVariable("itemId") Long id,
                               Model model) {
        logger.info("Called checkoutItem method");
        Client persistentClient = clientService.findByLogin(client.getLogin());
        ClientItem item = clientItemService.findById(id);

        model.addAttribute("clientItems", Collections.singletonList(item));
        model.addAttribute("client", persistentClient);

        return "checkoutPage";
    }
}
