package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Service.OrderService;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private ClientService clientService;
    private OrderService orderService;
    private ClientItemService clientItemService;
    private ItemService itemService;

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
        logger.debug("Setting clientItemService");
        this.clientItemService = clientItemService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        logger.debug("Setting itemService");
        this.itemService = itemService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String clientOrders(@AuthenticationPrincipal Client client,
                               Model model,
                               @PageableDefault(sort = {"createdOn"}, direction = Sort.Direction.DESC) Pageable pageable,
                               @CookieValue("jwtToken") String token) {
        logger.info("Called clientOrders method");

        Page<Order> orders = clientService.findOrdersByClientId(client.getId(), pageable, token);
        model.addAttribute("url", "/order?");
        model.addAttribute("orders", orders);

        return "order/ordersList";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String concreteClientOrder(@AuthenticationPrincipal Client client,
                                      @PathVariable("id") Long orderId,
                                      Model model,
                                      @CookieValue("jwtToken") String token) {
        logger.info("Called concreteClientOrder method");
        Order order = orderService.findById(orderId, client.getId(), token);
        model.addAttribute("order", order);

        return "order/concreteOrder";
    }

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkoutAllItems(@AuthenticationPrincipal Client client,
                                   Model model,
                                   HttpServletRequest request,
                                   @CookieValue("jwtToken") String token) {
        logger.info("Called checkoutAllItems method");
        List<ClientItem> basket = clientService.findBasketItemsByClientId(client.getId(), token);

        double generalPrice = clientItemService.generalPrice(client.getId(), token);
        double generalWeight = clientItemService.generalWeight(client.getId(), token);

        model.addAttribute("client", client);
        model.addAttribute("generalPrice", generalPrice);
        model.addAttribute("generalWeight", generalWeight);

        request.getSession().setAttribute("orderedItems", basket);

        return "order/checkoutPage";
    }

    @GetMapping("/checkout/{itemId}")
    @PreAuthorize("isAuthenticated()")
    public String checkoutItem(@AuthenticationPrincipal Client client,
                               @PathVariable("itemId") Long id,
                               Model model,
                               HttpServletRequest request,
                               @CookieValue("jwtToken") String token) {
        logger.info("Called checkoutItem method");
        ClientItem item = clientItemService.findById(client.getId(), id, token);
        double generalPrice = clientItemService.generalPrice(client.getId(), token);
        double generalWeight = clientItemService.generalWeight(client.getId(), token);

        model.addAttribute("client", client);
        model.addAttribute("generalPrice", generalPrice);
        model.addAttribute("generalWeight", generalWeight);

        request.getSession().setAttribute("orderedItems", Collections.singleton(item));

        return "order/checkoutPage";
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkoutOrder(@AuthenticationPrincipal Client client,
                                @RequestParam("payment") String paymentMethod,
                                @Valid @ModelAttribute("orderContacts") Contacts contacts,
                                BindingResult bindingResult,
                                Model model,
                                HttpServletRequest request,
                                @CookieValue("jwtToken") String token) {
        logger.info("Called checkoutOrder method");
        @SuppressWarnings("unchecked")
        Set<ClientItem> items = (Set<ClientItem>) request.getSession().getAttribute("orderedItems");
        double generalPrice = clientItemService.generalPrice(client.getId(), token);
        double generalWeight = clientItemService.generalWeight(client.getId(), token);

        if (bindingResult.hasErrors()) {
            logger.warn("Form with contact data contains errors");
            model.mergeAttributes(ValidateUtil.validate(bindingResult));
            model.addAttribute("contactsData", contacts);
            model.addAttribute("generalPrice", generalPrice);
            model.addAttribute("generalWeight", generalWeight);

            return "order/checkoutPage";
        }

        request.getSession().removeAttribute("orderedItems");
        clientService.deleteBasketItems(items, client.getId(), token);

        Order newOrder = new Order(items, contacts, paymentMethod);
        newOrder.setOrderStatus(OrderStatus.NEW);
        newOrder.setClient(client);
        orderService.createNewOrder(newOrder, client.getId(), token);

        items.forEach(i -> {
            i.getItem().setCount(i.getItem().getCount()-i.getQuantity());
            itemService.save(i.getItem());
        });

        if (paymentMethod.equals("Наложенный платёж")) {
            logger.info("Method payment is C.O.D");
            //Nothing
        } // Another methods...

        return "messages/orderCreated";
    }
}
