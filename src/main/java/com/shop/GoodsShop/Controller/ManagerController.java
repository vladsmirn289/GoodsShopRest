package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.Order;
import com.shop.GoodsShop.Model.OrderStatus;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.OrderService;
import com.shop.GoodsShop.Utils.URIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    private ClientService clientService;
    private OrderService orderService;

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

    @GetMapping("/manager")
    public String customOrders(@AuthenticationPrincipal Client manager,
                               Model model) {
        logger.info("Called customOrders method");
        model.addAttribute("orders", orderService.findOrdersForManagers());
        model.addAttribute("manager", manager);

        return "customOrders";
    }

    @GetMapping("/setManager/{id}")
    public String setManagerToOrder(@AuthenticationPrincipal Client manager,
                                    @PathVariable("id") Long orderId,
                                    Model model,
                                    RedirectAttributes redirectAttributes,
                                    @RequestHeader(required = false) String referer) {
        logger.info("Called setManagerToOrder method");
        Order order = orderService.findById(orderId);
        if (order.getManager() == null) {
            Client persistentManager = clientService.findByLogin(manager.getLogin());
            order.setManager(persistentManager);
            orderService.save(order);
        } else {
            logger.info("Conflict, another manager has already set himself to manager");
            model.addAttribute("conflictError", "Другой менеджер уже назначил себя менеджером!");
        }

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/editOrder/{id}")
    public String editOrder(@PathVariable("id") Long orderId,
                            Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);

        Set<OrderStatus> statuses = Arrays.stream(OrderStatus.values())
                .filter(status -> !status.equals(order.getOrderStatus()))
                .collect(Collectors.toSet());
        model.addAttribute("statuses", statuses);

        return "editOrderPage";
    }

    @PostMapping("/changeOrderStatus/{id}")
    public String changeOrderStatus(@PathVariable("id") Long orderId,
                                    @RequestParam("orderStatus") String status,
                                    RedirectAttributes redirectAttributes,
                                    @RequestHeader(required = false) String referer) {
        Order order = orderService.findById(orderId);
        order.setOrderStatus(OrderStatus.valueOf(status));
        orderService.save(order);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }
}
