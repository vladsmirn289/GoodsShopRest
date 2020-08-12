package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
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
        "classpath:db/H2/item-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Value("${jwt.user.long.term}")
    private String userToken;

    @BeforeEach
    public void init() {
        Item proGit = itemService.findById(7L);
        File image = new File("src/test/resources/images/proGit.jpg");
        FileUtil fileUtil = new FileUtil();
        proGit.setImage("proGit.jpg");

        itemService.save(proGit);
    }

    @Test
    public void testSearchItemsByKeyword() throws Exception {
        mockMvc
                .perform(get("/item")
                        .param("keyword", "фес")
                        .param("size", "1")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("item/categoryItems"))
                .andExpect(model().attribute("keyword", "фес"))
                .andExpect(model().attributeExists("items"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='itemsBlock']/div[1]/div/div/div[2]/div/h4/a")
                        .string("Spring 5 для профессионалов"));
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
    public void shouldShowErrorWhenTryFindByInvalidId() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/item/100"))
                .andDo(print())
                .andReturn();

        Exception exception = mvcResult.getResolvedException();
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Данный ресурс не найден");
    }

    @Test
    @WithUserDetails(value = "simpleUser")
    public void testAddItemsToBasket() throws Exception {
        mockMvc
                .perform(post("/item/6/addToBasket").cookie(new Cookie("jwtToken", userToken))
                            .with(csrf())
                            .param("quantity", "2")
                            .param("item_id", "6"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc
                .perform(get("/basket").cookie(new Cookie("jwtToken", userToken)))
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
