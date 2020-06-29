package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
