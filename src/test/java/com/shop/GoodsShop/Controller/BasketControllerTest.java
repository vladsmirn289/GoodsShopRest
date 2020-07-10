package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Repositories.ClientItemRepo;
import com.shop.GoodsShop.Service.ClientItemService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
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
@WithUserDetails(value = "simpleUser")
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientItemService clientItemService;

    @Autowired
    private ClientItemRepo clientItemRepo;

    @Autowired
    private ClientService clientService;

    @MockBean
    private InitDB initDB;

    @BeforeEach
    public void init() {
        Client client = clientService.findByLogin("simpleUser");
        ClientItem item1 = clientItemService.findById(16L);
        ClientItem item2 = clientItemService.findById(17L);
        ClientItem item3 = clientItemService.findById(18L);

        client.setBasket(new HashSet<>(Arrays.asList(item1, item2, item3)));
        clientService.save(client);
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
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[2]").string("Spring 5 для профессионалов"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]").string("2 шт."));
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
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]").string("1 шт."));

        assertThat(clientItemRepo.findAll().size()).isEqualTo(4);
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

        assertThat(clientItemRepo.findAll().size()).isEqualTo(2);
    }
}
