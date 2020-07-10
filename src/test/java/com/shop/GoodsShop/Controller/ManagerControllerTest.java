package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Service.InitDB;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        "classpath:db/H2/clientItem-test.sql",
        "classpath:db/H2/basket-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("manager")
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InitDB initDB;

    @Test
    public void showCustomOrdersTest() throws Exception {
        mockMvc
                .perform(get("/order/manager"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("manager/customOrders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("manager"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(2))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[3]")
                        .string(containsString("1,075")))
                .andExpect(xpath("/html/body/div/table/tbody/tr[2]/td[3]")
                        .string(containsString("850")));
    }

    @Test
    public void setSelfToManagerOrderTest() throws Exception {
        mockMvc
                .perform(get("/order/setManager/21")
                        .header("referer", "http://localhost/order/manager"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/manager"));

        mockMvc
                .perform(get("/order/manager"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[5]/a")
                        .string("Редактировать заказ"));
    }

    @Test
    public void showEditOrderPageTest() throws Exception {
        mockMvc
                .perform(get("/order/editOrder/20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("manager/editOrderPage"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(xpath("/html/body/div/div/div[8]")
                        .string(containsString("Зелёная д5 к64")));
    }

    @Test
    public void shouldChangeOrderStatus() throws Exception {
        mockMvc
                .perform(post("/order/changeOrderStatus/20")
                        .header("referer", "http://localhost/order/editOrder/20")
                        .with(csrf())
                        .param("orderStatus", "ON_THE_WAY"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/editOrder/20"));

        mockMvc
                .perform(get("/order/editOrder/20"))
                .andExpect(xpath("//select[@id='inputOrderStatus']/option[1]")
                        .string("ON_THE_WAY"));
    }

    @Test
    public void shouldUnpinHimself() throws Exception {
        mockMvc
                .perform(get("/order/unpinHimself/20")
                         .header("referer", "http://localhost/order/manager"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/manager"));

        mockMvc
                .perform(get("/order/manager"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[1]/a")
                        .string("20"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[5]/a[1]")
                        .string("Назначить себя менеджером"));
    }
}
