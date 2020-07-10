package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.InitDB;
import com.shop.GoodsShop.Service.ItemService;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

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
        "classpath:db/H2/item-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryControllerTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InitDB initDB;

    @Test
    public void showParentCategoryNoItemsTest() throws Exception {
        mockMvc
                .perform(get("/category/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/categoryItems"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(0));
    }

    @Test
    public void showCategoryItemsTest() throws Exception {
        Category category = categoryService.findById(3L);
        List<Item> items = itemService.findByCategory(category);

        mockMvc
                .perform(get("/category/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/categoryItems"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("items"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='itemsBlock']/div[1]/div/div/div[2]/div/h4")
                        .string("Spring 5 для профессионалов"))
                .andExpect(xpath("//div[@id='itemsBlock']/div[2]/div/div/div[2]/div/h4")
                        .string("Git для профессионального программиста"));
    }

    @Test
    public void showNoCategoryError() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/category/10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        Exception exception = result.getResolvedException();
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Такой категории не существует!");
    }
}
