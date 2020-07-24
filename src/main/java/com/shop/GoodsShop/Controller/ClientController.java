package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Utils.MailSenderUtil;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/client")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private ClientService clientService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private MailSenderUtil mailSenderUtil;

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        logger.debug("Setting authenticationManager");
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        logger.debug("Setting passwordEncoder");
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailSenderUtil(MailSenderUtil mailSenderUtil) {
        logger.debug("Setting mailSenderUtil");
        this.mailSenderUtil = mailSenderUtil;
    }

    @GetMapping("/activate/{code}")
    public String activateClient(@PathVariable("code") String activationCode) {
        logger.info("Called activateClient method");
        Client foundByActivationCode = clientService.findByConfirmationCode(activationCode);
        String rawPassword = foundByActivationCode.getPassword();
        clientService.save(foundByActivationCode);

        logger.info("Authenticate client...");
        clientService.authenticateClient(rawPassword, foundByActivationCode.getLogin(), authenticationManager);

        return "messages/successConfirmation";
    }

    @GetMapping("/personalRoom")
    @PreAuthorize("isAuthenticated()")
    public String personalRoomPage(@AuthenticationPrincipal Client client,
                                   Model model) {
        logger.info("Called personalRoomPage method");
        model.addAttribute("client", client);

        return "client/personalRoom";
    }

    @PostMapping("/personalRoom")
    @PreAuthorize("isAuthenticated()")
    public String changePersonalInfo(@AuthenticationPrincipal Client originalClient,
                                     @Valid @ModelAttribute("changedPerson") Client client,
                                     BindingResult bindingResult,
                                     Model model,
                                     HttpServletRequest request) {
        logger.info("Called changePersonalInfo method");
        boolean clientExists = !originalClient.getLogin().equals(client.getLogin())
                && clientService.findByLogin(client.getLogin()) != null;

        if ( bindingResult.hasErrors() || clientExists ) {
            logger.warn("Personal room page has errors!");
            model.mergeAttributes(ValidateUtil.validate(bindingResult));

            if (clientExists) {
                logger.error("User with same login is already exists");
                model.addAttribute("userExistsError", "Пользователь с таким логином уже существует");
            }

            model.addAttribute("client", client);
            return "client/personalRoom";
        }

        if (!originalClient.getLogin().equals(client.getLogin())) {
            SecurityContextHolder.getContext().setAuthentication(null);
            request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
            clientService.save(client);
            return "redirect:";
        }

        clientService.save(client);
        logger.info("Change info successful");

        model.addAttribute("client", client);
        return "client/personalRoom";
    }

    @GetMapping("/resetPasswordRequest")
    @PreAuthorize("isAuthenticated()")
    public String resetPasswordRequest() {
        logger.info("Called changePasswordRequest method");

        return "client/resetPassword";
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("isAuthenticated()")
    public String resetPassword(@AuthenticationPrincipal Client client,
                                @RequestParam("email") String email,
                                Model model) {
        logger.info("Called sendChangePasswordRequest");

        String login = client.getLogin();
        try {
            mailSenderUtil.sendMessage(
                    email,
                    "Сброс пароля",
                    "Здравствуйте " + login + "," +
                            "чтобы сбросить пароль, перейдите по" +
                            "данной ссылке: http://localhost:8080/client/setNewPassword/" +
                            login + ".\nПосле перехода, ваш пароль автоматически изменится на \"12345\"");
        } catch (Exception e) {
            logger.error(e.toString());
            model.addAttribute("mailError", "Не удалось отправить письмо");
            model.addAttribute("email", email);

            return "client/resetPassword";
        }

        model.addAttribute("client", client);
        return "client/personalRoom";
    }

    @GetMapping("/setNewPassword/{login}")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordPage(@PathVariable("login") String login,
                                     @AuthenticationPrincipal Client client) {
        logger.info("Called changePasswordPage method");
        if (login.equals(client.getLogin())) {
            client.setPassword(passwordEncoder.encode("12345"));
            clientService.save(client);
        } else {
            return "messages/sessionExpired";
        }

        return "client/changePassword";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@AuthenticationPrincipal Client client,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("retypePassword") String retypePassword,
                                 Model model) {
        logger.info("Called changePassword method");
        boolean hasErrors = false;

        if (newPassword.length() < 5) {
            logger.error("Small password");
            model.addAttribute("lengthPasswordError", "Пароль должен состоять из как минимум 5 символов");
            hasErrors = true;
        }

        if (!newPassword.equals(retypePassword)) {
            logger.error("Passwords are not matching");
            model.addAttribute("retypePasswordError", "Пароли не совпадают!");
            hasErrors = true;
        }

        if (hasErrors) {
            return "client/changePassword";
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientService.save(client);

        return "messages/passwordSuccessfulChanged";
    }

    @GetMapping("/changeEmail")
    @PreAuthorize("isAuthenticated()")
    public String changeEmailPage() {
        logger.info("Called changeEmailPage method");

        return "client/changeEmailPage";
    }

    @PostMapping("/changeEmail")
    @PreAuthorize("isAuthenticated()")
    public String sendCodeForSetNewEmail(@AuthenticationPrincipal Client client,
                                         @RequestParam("email") String email,
                                         Model model) {
        logger.info("Called changeEmail method");
        String code = UUID.randomUUID().toString();
        client.setConfirmationCode(code);

        try {
            mailSenderUtil.sendTemplateMessage(
                    email,
                    client.getLogin(),
                    "http://localhost:8080/client/setNewEmail/" + email + "/" + code);
        } catch (Exception e) {
            logger.error(e.toString());
            model.addAttribute("mailError", "Не удалось отправить письмо");

            return "client/changeEmailPage";
        }

        clientService.save(client);
        return "messages/setNewEmailMessage";
    }

    @GetMapping("/setNewEmail/{email}/{code}")
    public String changeEmail(@PathVariable("email") String email,
                              @PathVariable("code") String code) {
        logger.info("Called changeEmail method");
        Client foundByCode = clientService.findByConfirmationCode(code);
        foundByCode.setConfirmationCode(null);
        foundByCode.setEmail(email);
        clientService.save(foundByCode);

        return "messages/successfulNewEmail";
    }
}
