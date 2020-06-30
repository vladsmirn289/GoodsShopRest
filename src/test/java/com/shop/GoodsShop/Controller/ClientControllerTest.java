package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.InitDB;
import com.shop.GoodsShop.Utils.MailSenderUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
        "classpath:db/H2/user-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @SpyBean
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MailSenderUtil mailSenderUtil;

    @MockBean
    private InitDB initDB;

    @Test
    @SuppressWarnings("deprecation")
    public void shouldActivateClient() throws Exception {
        Mockito
                .doNothing()
                .when(clientService)
                .authenticateClient(eq("01112"), eq("userWithCode"), anyObject());

        mockMvc
                .perform(get("/client/activate/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successConfirmation"));

        Client client = clientService.findByLogin("userWithCode");
        String password = client.getPassword();
        assertThat(client.getConfirmationCode()).isNull();
        assertThat(passwordEncoder.matches("01112", password));

        Mockito
                .verify(clientService, Mockito.times(1))
                    .authenticateClient(eq("01112"), eq("userWithCode"), any());
    }

    @Test
    public void notAuthenticatedWhenGoToPersonalRoomPage() throws Exception {
        mockMvc
                .perform(get("/client/personalRoom"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void showPersonalRoomPageTest() throws Exception {
        mockMvc
                .perform(get("/client/personalRoom"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("personalRoom"))
                .andExpect(model().attribute("client", clientService.findByLogin("simpleUser")));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldSuccessChangingClientInfo() throws Exception {
        mockMvc
                .perform(post("/client/personalRoom")
                        .with(csrf())
                        .param("id", "12")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("login", "user")
                        .param("patronymic", "")
                        .param("email", "g@g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("personalRoom"))
                .andExpect(model().attributeExists("client"));

        Client client = clientService.findByLogin("user");
        assertThat(client).isNotNull();
        assertThat(client.getId()).isEqualTo(12L);
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldErrorShowWhenChangingPersonalInfoWithWrongData() throws Exception {
        mockMvc
                .perform(post("/client/personalRoom")
                        .with(csrf())
                        .param("id", "12")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("login", "")
                        .param("patronymic", "")
                        .param("email", "g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("personalRoom"))
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrors(
                        "changedPerson", "firstName",
                        "lastName", "login",
                        "email"))
                .andExpect(model().attributeExists("firstNameError"))
                .andExpect(model().attributeExists("lastNameError"))
                .andExpect(model().attributeExists("loginError"))
                .andExpect(model().attributeExists("emailError"))
                .andExpect(model().attributeDoesNotExist("userExistsError"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldErrorUserExistsWhenTryingChangePersonalInfo() throws Exception {
        mockMvc
                .perform(post("/client/personalRoom")
                        .with(csrf())
                        .param("id", "12")
                        .param("firstName", "test")
                        .param("lastName", "user")
                        .param("login", "admin")
                        .param("patronymic", "")
                        .param("email", "g@g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("personalRoom"))
                .andExpect(model().attributeExists("userExistsError"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void showChangePasswordPageTest() throws Exception {
        mockMvc
                .perform(get("/client/changePassword"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("changePassword"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldShowErrorWhenTryChangePasswordWithIncorrectData() throws Exception {
        mockMvc
                .perform(post("/client/changePassword")
                         .with(csrf())
                         .param("currentPassword", "67890")
                         .param("newPassword", "1")
                         .param("retypePassword", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("changePassword"))
                .andExpect(model().attribute("currentPasswordError", "Неверный пароль"))
                .andExpect(model().attribute("lengthPasswordError", "Пароль должен состоять из как минимум 5 символов"))
                .andExpect(model().attribute("retypePasswordError", "Пароли не совпадают!"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldCorrectChangePassword() throws Exception {
        mockMvc
                .perform(post("/client/changePassword")
                        .with(csrf())
                        .param("currentPassword", "12345")
                        .param("newPassword", "1234567890")
                        .param("retypePassword", "1234567890"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/passwordSuccessfulChanged"))
                .andExpect(model().attributeDoesNotExist("currentPasswordError"))
                .andExpect(model().attributeDoesNotExist("lengthPasswordError"))
                .andExpect(model().attributeDoesNotExist("retypePasswordError"));

        Client client = clientService.findByLogin("simpleUser");
        assertThat(passwordEncoder.matches("1234567890", client.getPassword()));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void showChangeEmailPageTest() throws Exception {
        mockMvc
                .perform(get("/client/changeEmail"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("changeEmailPage"));
    }

    @Test
    @WithUserDetails("simpleUser")
    public void shouldSendCodeForActivationEmail() throws Exception {
        mockMvc
                .perform(post("/client/changeEmail")
                         .with(csrf())
                         .param("email", "g@g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/setNewEmailMessage"));

        Client client = clientService.findByLogin("simpleUser");
        String password = client.getPassword();
        assertThat(client.getConfirmationCode()).isNotNull();
        assertThat(client.getEmail()).isEqualTo("vladsmirn289@gmail.com");
        assertThat(passwordEncoder.matches("12345", password));

        Mockito
                .verify(mailSenderUtil, Mockito.times(1))
                .sendTemplateMessage(eq("g@g"),
                        eq("simpleUser"),
                        contains("http://localhost:8080/client/setNewEmail/g@g"));
    }

    @Test
    @WithUserDetails("simpleUser")
    @SuppressWarnings("deprecation")
    public void shouldRaiseExceptionWhenTryToChangeEmail() throws Exception {
        Mockito
                .doThrow(RuntimeException.class)
                .when(mailSenderUtil)
                .sendTemplateMessage(eq("g@g"), eq("simpleUser"), anyObject());

        mockMvc
                .perform(post("/client/changeEmail")
                        .with(csrf())
                        .param("email", "g@g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("changeEmailPage"))
                .andExpect(model().attributeExists("mailError"));

        Client client = clientService.findByLogin("simpleUser");
        String password = client.getPassword();
        assertThat(client.getConfirmationCode()).isNull();
        assertThat(client.getEmail()).isEqualTo("vladsmirn289@gmail.com");
        assertThat(passwordEncoder.matches("12345", password));

        Mockito
                .verify(mailSenderUtil, Mockito.times(1))
                .sendTemplateMessage(eq("g@g"),
                        eq("simpleUser"),
                        contains("http://localhost:8080/client/setNewEmail/g@g"));
    }

    @Test
    public void shouldConfirmChangeEmail() throws Exception {
        mockMvc
                .perform(get("/client/setNewEmail/m@m/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("messages/successfulNewEmail"));

        Client client = clientService.findByLogin("userWithCode");

        assertThat(client.getConfirmationCode()).isNull();
        assertThat(client.getEmail()).isEqualTo("m@m");
        assertThat(client.getPassword()).isEqualTo("01112");
    }
}
