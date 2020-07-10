package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.InitDB;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.FileUtil;
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
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
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
        "classpath:db/H2/item-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @MockBean
    private InitDB initDB;

    @BeforeEach
    public void init() {
        Item proGit = itemService.findById(7L);
        File image = new File("src/test/resources/images/proGit.jpg");
        FileUtil fileUtil = new FileUtil();
        proGit.setImage(fileUtil.fileToBytes(image));

        itemService.save(proGit);
    }

    @Test
    public void testSearchItemsByKeyword() throws Exception {
        mockMvc
                .perform(get("/item")
                        .param("keyword", "фес"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/categoryItems"))
                .andExpect(model().attribute("keyword", "фес"))
                .andExpect(model().attributeExists("items"))
                .andExpect(xpath("//div[@id=\"itemsBlock\"]/div").nodeCount(2));
    }

    @Test
    public void testFailDownloadImage() throws Exception {
        mockMvc
                .perform(get("/item/6/image"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testDownloadImage() throws Exception {
        File image = new File("src/test/resources/images/proGit.jpg");
        FileUtil fileUtil = new FileUtil();
        mockMvc
                .perform(get("/item/7/image"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(fileUtil.fileToBytes(image)));
    }

    @Test
    public void testNoItemError() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/item/100/image"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        Exception exception = result.getResolvedException();
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Такого предмета не существует!");
    }

    @Test
    public void testGetItemPage() throws Exception {
        mockMvc
                .perform(get("/item/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/itemPage"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("item"))
                .andExpect(xpath("/html/body/div/div[1]/div/div[2]/div/h5")
                        .string("Spring 5 для профессионалов"));
    }

    @Test
    @WithUserDetails(value = "simpleUser")
    public void testAddItemsToBasket() throws Exception {
        mockMvc
                .perform(post("/item/6/addToBasket")
                            .with(csrf())
                            .param("quantity", "2")
                            .param("item_id", "6"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc
                .perform(get("/basket"))
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1));
    }

    @Test
    public void testAddItemsToBasketNotAuthenticated() throws Exception {
        mockMvc
                .perform(post("/item/6/addToBasket")
                        .with(csrf())
                        .param("quantity", "2")
                        .param("item_id", "6"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
