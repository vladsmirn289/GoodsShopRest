package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Utils.MailSenderUtil;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private ClientService clientService;
    private MailSenderUtil mailSenderUtil;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setMailSenderUtil(MailSenderUtil mailSenderUtil) {
        logger.debug("Setting mailSender");
        this.mailSenderUtil = mailSenderUtil;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationPage() {
        logger.info("Called showRegistrationPage method");
        return "security/registration";
    }

    @PostMapping
    public String registerClient(@RequestParam("passwordRepeat") String passwordRepeat,
                                 @Valid @ModelAttribute("newClient") Client client,
                                 BindingResult bindingResult,
                                 Model model) {
        logger.info("Called registerClient method");
        boolean passwordsIsMatch = passwordRepeat.equals(client.getPassword());
        boolean clientExists;

        try {
            clientService.loadUserByUsername(client.getLogin());
            clientExists = true;
        } catch (UsernameNotFoundException ex) {
            logger.warn(ex.toString());
            clientExists = false;
        }

        if ( bindingResult.hasErrors() || !passwordsIsMatch || clientExists ) {
            logger.warn("Registration page has errors!");
            model.mergeAttributes(ValidateUtil.validate(bindingResult));

            if (!passwordsIsMatch) {
                logger.error("Passwords are not matching");
                model.addAttribute("passwordRepeatError", "Пароли не совпадают");
            }

            if (clientExists) {
                logger.error("User with same login is already exists");
                model.addAttribute("userExistsError", "Пользователь с таким логином уже существует");
            }

            model.addAttribute("client", client);
            return "security/registration";
        } else {
            logger.info("Registration successful");
        }

        String code = UUID.randomUUID().toString();
        client.setConfirmationCode(code);

        try {
            mailSenderUtil.sendTemplateMessage(
                    client.getEmail(),
                    client.getLogin(),
                    "http://localhost:8080/client/activate/" + code);
        } catch (Exception e) {
            logger.error(e.toString());
            model.addAttribute("client", client);
            model.addAttribute("mailError", "Не удалось отправить письмо");

            return "security/registration";
        }

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setNonLocked(false);
        clientService.save(client, null);
        return "messages/needConfirmation";
    }
}
