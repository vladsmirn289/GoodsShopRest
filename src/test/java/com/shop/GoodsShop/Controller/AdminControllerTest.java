package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.InitDB;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        "classpath:db/H2/clientItem-test.sql",
        "classpath:db/H2/basket-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("admin")
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @MockBean
    private InitDB initDB;

    @Test
    @WithUserDetails("simpleUser")
    public void accessDeniedTest() throws Exception {
        mockMvc
                .perform(get("/admin"))
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    public void showAdminFunctionsTest() throws Exception {
        mockMvc
                .perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/adminFunctions"));
    }

    @Test
    public void showListOfUsersTest() throws Exception {
        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/listOfUsers"))
                .andExpect(model().attributeExists("users"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(4));
    }

    @Test
    public void addManagerTest() throws Exception {
        mockMvc
                .perform(get("/admin/addManager/12")
                         .header("referer", "http://localhost/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listOfUsers"));

        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]")
                        .string("simpleUser"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[7]")
                        .string(containsString("Открепить менеджера")));
    }

    @Test
    public void addAdminTest() throws Exception {
        mockMvc
                .perform(get("/admin/addAdmin/12")
                        .header("referer", "http://localhost/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listOfUsers"));

        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]")
                        .string("simpleUser"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[7]/a")
                        .string("Установить менеджером"));
    }

    @Test
    public void removeManagerTest() throws Exception {
        mockMvc
                .perform(get("/admin/removeManager/13")
                        .header("referer", "http://localhost/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listOfUsers"));

        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[2]/td[4]")
                        .string("manager"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[2]/td[7]/a")
                        .string(containsString("Установить менеджером")));
    }

    @Test
    public void lockUnlockAccountTest() throws Exception {
        mockMvc
                .perform(get("/admin/lockAccount/12")
                        .header("referer", "http://localhost/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listOfUsers"));

        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]")
                        .string("simpleUser"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[7]")
                        .string(containsString("Разблокировать пользователя")));

        mockMvc
                .perform(get("/admin/unlockAccount/12")
                        .header("referer", "http://localhost/admin/listOfUsers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listOfUsers"));

        mockMvc
                .perform(get("/admin/listOfUsers"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[4]")
                        .string("simpleUser"))
                .andExpect(xpath("/html/body/div/table/tbody/tr[1]/td[7]")
                        .string(containsString("Заблокировать пользователя")));
    }

    @Test
    public void showCreateCategoryPageTest() throws Exception {
        mockMvc
                .perform(get("/admin/createCategory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createCategory"))
                .andExpect(model().attributeExists("parents"));
    }

    @Test
    public void successCreateNewParentCategoryTest() throws Exception {
        mockMvc
                .perform(post("/admin/createCategory")
                         .with(csrf())
                         .param("category", "Родительская категория")
                         .param("name", "Канцелярия"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successfulCreatedCategory"))
                .andExpect(model().attributeDoesNotExist("nameError"));

        List<Category> categories = categoryService.findByParentIsNull();
        assertThat(categories.size()).isEqualTo(3);
    }

    @Test
    public void successCreateNewChildCategoryTest() throws Exception {
        mockMvc
                .perform(post("/admin/createCategory")
                        .with(csrf())
                        .param("category", "Электроника")
                        .param("name", "Телефоны"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successfulCreatedCategory"))
                .andExpect(model().attributeDoesNotExist("nameError"));

        Category parent = categoryService.findByName("Электроника");
        List<Category> categories = categoryService.findByParent(parent);
        assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    public void shouldShowErrorWhenTryCreateCategoryWithBlankNameTest() throws Exception {
        mockMvc
                .perform(post("/admin/createCategory")
                        .with(csrf())
                        .param("category", "Электроника")
                        .param("name", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createCategory"))
                .andExpect(model().attribute("name", ""))
                .andExpect(model().attribute("nameError", "Имя категории не может быть пустым"));
    }

    @Test
    public void shouldShowErrorWhenTryCreateCategoryWithExistsNameTest() throws Exception {
        mockMvc
                .perform(post("/admin/createCategory")
                        .with(csrf())
                        .param("category", "Родительская категория")
                        .param("name", "Программирование"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createCategory"))
                .andExpect(model().attribute("name", "Программирование"))
                .andExpect(model().attribute("nameError", "Категория с таким именем уже существует"));
    }

    @Test
    public void showCreateItemPageTest() throws Exception {
        mockMvc
                .perform(get("/admin/createItem"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createItem"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void showUpdateItemPageTest() throws Exception {
        mockMvc
                .perform(get("/admin/updateItem/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createItem"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void shouldSuccessCreateNewItem() throws Exception {
        FileUtil fileUtil = new FileUtil();
        File testFile = new File("src/test/resources/images/proGit.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "proGit.jpg", "image/jpg", fileUtil.fileToBytes(testFile));

        MockHttpServletRequestBuilder builder = multipart("/admin/createOrUpdateItem")
                .file(multipartFile)
                .with(csrf())
                .param("name", "newItem")
                .param("count", "30")
                .param("weight", "0.4")
                .param("price", "56")
                .param("description", "This is test description")
                .param("characteristics", "This is test characteristics")
                .param("category", "Программирование");

        mockMvc
                .perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successfulItemCreated"));

        mockMvc
                .perform(get("/category/3"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(3));
    }

    @Test
    public void shouldShowErrorsWhenTryToCreateNewItem() throws Exception {
        FileUtil fileUtil = new FileUtil();
        File testFile = new File("src/test/resources/images/proGit.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "proGit", "image/jpg", fileUtil.fileToBytes(testFile));

        MockHttpServletRequestBuilder builder = multipart("/admin/createOrUpdateItem")
                .file(multipartFile)
                .with(csrf())
                .param("name", "")
                .param("count", "0")
                .param("weight", "1")
                .param("price", "1")
                .param("description", "desc...")
                .param("characteristics", "")
                .param("category", "Программирование");

        mockMvc
                .perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createItem"))
                .andExpect(model().attributeExists("nameError"))
                .andExpect(model().attributeExists("descriptionError"))
                .andExpect(model().attributeExists("characteristicsError"))
                .andExpect(model().attributeExists("fileExtError"))
                .andExpect(model().attributeExists("item"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void shouldSuccessUpdateItem() throws Exception {
        FileUtil fileUtil = new FileUtil();
        File testFile = new File("src/test/resources/images/proGit.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "proGit.jpg", "image/jpg", fileUtil.fileToBytes(testFile));

        MockHttpServletRequestBuilder builder = multipart("/admin/createOrUpdateItem")
                .file(multipartFile)
                .with(csrf())
                .param("id", "6")
                .param("name", "newItem")
                .param("count", "30")
                .param("weight", "0.4")
                .param("price", "56")
                .param("description", "This is test description")
                .param("characteristics", "This is test characteristics")
                .param("category", "Научная литература");

        mockMvc
                .perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successfulItemUpdated"));

        mockMvc
                .perform(get("/category/3"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(1));

        mockMvc
                .perform(get("/category/2"))
                .andExpect(xpath("//div[@id='itemsBlock']/div").nodeCount(3));

        Item item = itemService.findById(6L);
        assertThat(item.getName()).isEqualTo("newItem");
        assertThat(item.getCount()).isEqualTo(30);
        assertThat(item.getWeight()).isEqualTo(0.4);
        assertThat(item.getPrice()).isEqualTo(56);
        assertThat(item.getDescription()).isEqualTo("This is test description");
        assertThat(item.getCharacteristics()).isEqualTo("This is test characteristics");
        assertThat(item.getCategory().getName()).isEqualTo("Научная литература");
    }
}
