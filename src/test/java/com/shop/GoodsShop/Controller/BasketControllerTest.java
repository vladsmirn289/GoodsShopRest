package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(value = {
        "classpath:db/H2/after-test.sql",
        "classpath:db/H2/category-test.sql",
        "classpath:db/H2/user-test.sql",
        "classpath:db/H2/item-test.sql",
        "classpath:db/H2/order-test.sql",
        "classpath:db/H2/clientItem-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(value = "simpleUser")
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemService;

    @Value("${jwt.admin.long.term}")
    private String longTermJwt;

    @BeforeEach
    public void init() {
        Client client = clientService.findByLogin("simpleUser", longTermJwt);
        Item item1 = itemService.findById(6L);
        Item item2 = itemService.findById(7L);
        Item item3 = itemService.findById(8L);

        ClientItem cItem1 = new ClientItem(item1, 2);
        ClientItem cItem2 = new ClientItem(item2, 1);
        ClientItem cItem3 = new ClientItem(item3, 3);

        client.setBasket(new HashSet<>(Arrays.asList(cItem1, cItem2, cItem3)));
        clientService.save(client, longTermJwt);
    }

    @Test
    public void showBasketItemsTest() throws Exception {
        mockMvc
                .perform(get("/basket"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("basket/basket"))
                .andExpect(model().attributeExists("basketItems"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(3))
                .andExpect(xpath("/html/body/div/table/tbody")
                        .string(containsString("Spring 5 для профессионалов")))
                .andExpect(xpath("/html/body/div/table/tbody")
                        .string(containsString("Производные и интегралы")))
                .andExpect(xpath("/html/body/div/table/tbody")
                        .string(containsString("Git для профессионального программиста")));
    }

    @Test
    public void deleteItemFromBasketTest() throws Exception {
        mockMvc
                .perform(get("/basket/delete/16"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket"));

        mockMvc
                .perform(get("/basket"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(2))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[2]").string("Производные и интегралы"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]").string("3 шт."));
    }

    @Test
    public void deleteAllItemFromBasketTest() throws Exception {
        mockMvc
                .perform(get("/basket/delete"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket"));

        mockMvc
                .perform(get("/basket"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(0));
    }
}
