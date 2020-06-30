package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private ClientService clientService;
    private AuthenticationManager authenticationManager;

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

    @GetMapping("/activate/{code}")
    public String activateClient(@PathVariable("code") String activationCode) {
        logger.info("Called activateClient method");
        Client foundedByActivationCode = clientService.findByConfirmationCode(activationCode);
        String rawPassword = foundedByActivationCode.getPassword();
        clientService.save(foundedByActivationCode);

        logger.info("Authenticate client...");
        clientService.authenticateClient(rawPassword, foundedByActivationCode.getLogin(), authenticationManager);

        return "util/successConfirmation";
    }

    @GetMapping("/personalRoom")
    @PreAuthorize("isAuthenticated()")
    public String personalRoomPage(@AuthenticationPrincipal Client client,
                                   Model model) {
        logger.info("Called personalRoomPage method");
        model.addAttribute("client", clientService.findByLogin(client.getLogin()));

        return "personalRoom";
    }

    @PostMapping("/personalRoom")
    public String changePersonalInfo(@Valid @ModelAttribute("changedPerson") Client client,
                                     BindingResult bindingResult,
                                     Model model) {
        logger.info("Called changePersonalInfo method");
        Client originalClient = clientService.findById(client.getId());
        Client persistentClient = clientService.findByLogin(client.getLogin());
        boolean clientExists = (!originalClient.getLogin().equals(client.getLogin()))
                && (persistentClient != null);

        if ( bindingResult.hasErrors() || clientExists ) {
            logger.warn("Personal room page has errors!");
            model.mergeAttributes(ValidateUtil.validate(bindingResult));

            if (clientExists) {
                logger.error("User with same login is already exists");
                model.addAttribute("userExistsError", "Пользователь с таким логином уже существует");
            }

            model.addAttribute("client", client);
            return "personalRoom";
        } else {
            if (!client.getFirstName().equals(originalClient.getFirstName())) {
                originalClient.setFirstName(client.getFirstName());
            }

            if (!client.getLastName().equals(originalClient.getLastName())) {
                originalClient.setLastName(client.getLastName());
            }

            if (!client.getPatronymic().equals(originalClient.getPatronymic())) {
                originalClient.setPatronymic(client.getPatronymic());
            }

            if (!client.getLogin().equals(originalClient.getLogin())) {
                originalClient.setLogin(client.getLogin());
            }

            clientService.save(originalClient);
            logger.info("Change info successful");
        }

        model.addAttribute("client", originalClient);
        return "personalRoom";
    }
}
