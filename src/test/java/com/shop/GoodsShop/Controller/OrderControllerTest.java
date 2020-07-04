package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.InitDB;
import com.shop.GoodsShop.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(value = {
        "classpath:db/H2/category-test.sql",
        "classpath:db/H2/user-test.sql",
        "classpath:db/H2/item-test.sql",
        "classpath:db/H2/order-test.sql",
        "classpath:db/H2/clientItem-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientItemService clientItemService;

    @MockBean
    private InitDB initDB;

    @Test
    @WithUserDetails("simpleUser")
    public void showOrdersListTest() throws Exception {
        mockMvc
                .perform(get("/order"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ordersList"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(2))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[3]").string(containsString("8,000")))
                .andExpect(xpath("/html/body/div/table/tbody/tr[2]/td[3]").string(containsString("1,075")));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void showConcreteClientOrderTest() throws Exception {
        mockMvc
                .perform(get("/order/19"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("concreteOrder"))
                .andExpect(model().attribute("order", orderService.findById(19L)))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr/td[3]").string("Spring 5 для профессионалов"));
    }

    @Test
    @WithUserDetails("simpleUser")
    @Transactional
    public void checkoutAllItemsTest() throws Exception {
        mockMvc
                .perform(get("/order/checkout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("checkoutPage"))
                .andExpect(model().attribute("clientItems", clientService.findByLogin("simpleUser").getBasket()))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void checkoutItemTest() throws Exception {
        mockMvc
                .perform(get("/order/checkout/16"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("checkoutPage"))
                .andExpect(model().attribute("clientItems", Collections.singletonList(clientItemService.findById(16L))))
                .andExpect(model().attributeExists("client"));
    }
}
