package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Order;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application.properties")
@Sql(value = {
        "classpath:db/H2/after-test.sql",
        "classpath:db/H2/category-test.sql",
        "classpath:db/H2/user-test.sql",
        "classpath:db/H2/item-test.sql",
        "classpath:db/H2/order-test.sql",
        "classpath:db/H2/clientItem-test.sql",
        "classpath:db/H2/basket-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails("simpleUser")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemService;

    @Value("${jwt.admin.long.term}")
    private String adminToken;

    @Value("${jwt.user.long.term}")
    private String userToken;

    @Test
    public void showOrdersListTest() throws Exception {
        mockMvc
                .perform(get("/order").cookie(new Cookie("jwtToken", userToken))
                         .param("size", "1")
                         .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/ordersList"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[3]")
                        .string(containsString("8,000")));

        mockMvc
                .perform(get("/order").cookie(new Cookie("jwtToken", userToken))
                        .param("size", "1")
                        .param("page", "1"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[3]")
                        .string(containsString("1,075")));
    }

    @Test
    public void showConcreteClientOrderTest() throws Exception {
        mockMvc
                .perform(get("/order/19").cookie(new Cookie("jwtToken", userToken)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/concreteOrder"))
                .andExpect(model().attribute("order", orderService.findById(19L, 12L, userToken)))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[3]")
                        .string("Spring 5 для профессионалов"));
    }

    @Test
    public void checkoutAllItemsTest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/order/checkout").cookie(new Cookie("jwtToken", userToken)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/checkoutPage"))
                .andExpect(model().attributeExists("client"))
                .andReturn();

        Object attribute = mvcResult.getRequest().getSession(false)
                .getAttribute("orderedItems");
        assertThat(attribute).isNotNull();
    }

    @Test
    public void checkoutItemTest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/order/checkout/16").cookie(new Cookie("jwtToken", adminToken)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/checkoutPage"))
                .andExpect(model().attributeExists("client"))
                .andReturn();

        Object attribute = mvcResult.getRequest().getSession(false)
                .getAttribute("orderedItems");
        assertThat(attribute).isNotNull();
    }

    @Test
    public void checkoutSuccessfulOrderTest() throws Exception {
        Client client = clientService.findByLogin("simpleUser", userToken);
        Set<ClientItem> basket = new HashSet<>(clientService.findBasketItemsByClientId(client.getId(), userToken));

        MvcResult mvcResult = mockMvc
                .perform(post("/order/checkout").cookie(new Cookie("jwtToken", userToken))
                         .with(csrf())
                         .sessionAttr("orderedItems", basket)
                         .param("generalWeight", "11")
                         .param("generalPrice", "221980")
                         .param("city", "Moscow")
                         .param("zipCode", "123456")
                         .param("country", "Russia")
                         .param("phoneNumber", "1234567890")
                         .param("street", "testStreet")
                         .param("payment", "Наложенный платёж"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("messages/orderCreated"))
                .andReturn();

        Object attribute = mvcResult.getRequest().getSession(false)
                .getAttribute("orderedItems");
        assertThat(attribute).isNull();

        assertThat(client.getBasket()).isEmpty();

        Pageable pageable = PageRequest.of(2, 1, Sort.by("id"));
        List<Order> orders = clientService.findOrdersByClientId(12L, pageable, userToken).getContent();

        assertThat(orders.get(0).getClientItems().size()).isEqualTo(2);
        assertThat(itemService.findById(11L).getCount()).isEqualTo(197);
    }

    @Test
    public void shouldErrorShowWhenCheckoutOrderWithWrongData() throws Exception {
        Client client = clientService.findByLogin("simpleUser", userToken);
        Set<ClientItem> basket = new HashSet<>(clientService.findBasketItemsByClientId(client.getId(), userToken));

        mockMvc
                .perform(post("/order/checkout").cookie(new Cookie("jwtToken", userToken))
                        .with(csrf())
                        .sessionAttr("orderedItems", basket)
                        .param("city", "")
                        .param("zipCode", "")
                        .param("country", "Russia")
                        .param("phoneNumber", "")
                        .param("street", "")
                        .param("payment", "Наложенный платёж"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/checkoutPage"))
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrors(
                        "orderContacts", "city",
                        "zipCode", "phoneNumber", "street"))
                .andExpect(model().attributeExists("cityError"))
                .andExpect(model().attributeExists("zipCodeError"))
                .andExpect(model().attributeExists("phoneNumberError"))
                .andExpect(model().attributeExists("streetError"))
                .andExpect(model().attributeExists("contactsData"))
                .andExpect(model().attributeExists("generalPrice"))
                .andExpect(model().attributeExists("generalWeight"));
    }

    @Test
    public void showCustomOrdersAccessDeniedTest() throws Exception {
        mockMvc
                .perform(get("/order/manager").cookie(new Cookie("jwtToken", userToken)))
                .andDo(print())
                .andExpect(status().is(403));
    }
}
