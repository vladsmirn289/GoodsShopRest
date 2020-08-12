package com.shop.GoodsShop.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application.properties")
@Sql(value = {
        "classpath:db/H2/after-test.sql",
        "classpath:db/H2/category-test.sql",
        "classpath:db/H2/item-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
        mockMvc
                .perform(get("/category/3")
                         .param("size", "1")
                         .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/categoryItems"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("url", "/category/3?"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='itemsBlock']/div[1]/div/div/div[2]/div/h4/a")
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
        assertThat(exception.getMessage()).isEqualTo("Данный ресурс не найден");
    }
}
