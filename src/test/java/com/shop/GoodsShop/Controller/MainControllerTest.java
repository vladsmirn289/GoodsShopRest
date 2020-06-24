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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(value = {
        "classpath:db/H2/category-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InitDB initDB;

    @Test
    public void showMainPageTest() throws Exception {
        mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("treeOfCategories"))
                .andExpect(xpath("//div[@id='categoryBlock']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='collapseExample1']/a").nodeCount(2))
                .andExpect(xpath("//*[@id='collapseExample4']/a").nodeCount(1));
    }
}
