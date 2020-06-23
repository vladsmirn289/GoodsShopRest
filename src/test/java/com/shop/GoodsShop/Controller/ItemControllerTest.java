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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void testFailDownloadImage() throws Exception {
        mockMvc
                .perform(get("/item/6/image"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testDownloadImage() throws Exception {
        File image = new File("src/test/resources/images/proGit.jpg");
        FileUtil fileUtil = new FileUtil();
        mockMvc
                .perform(get("/item/7/image"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(fileUtil.fileToBytes(image)));
    }

    @Test
    public void testNoItemError() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/item/100/image"))
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
                .andExpect(status().isOk())
                .andExpect(view().name("itemPage"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("item"))
                .andExpect(xpath("/html/body/div/div[1]/div/div[2]/div/h5")
                        .string("Spring 5 для профессионалов"));
    }
}
