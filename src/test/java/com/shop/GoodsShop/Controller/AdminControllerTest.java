package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Service.CategoryService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}
