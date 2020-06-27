package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String showRegistrationPage(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerClient(@RequestParam("passwordRepeat") String passwordRepeat,
                                 @Valid @ModelAttribute("newClient") Client client,
                                 BindingResult bindingResult,
                                 Model model) {
        boolean passwordsIsMatch = passwordRepeat.equals(client.getPassword());
        boolean clientExists = clientService.findByLogin(client.getLogin()) != null;

        if ( bindingResult.hasErrors() || !passwordsIsMatch || clientExists ) {
            model.mergeAttributes(ValidateUtil.validate(bindingResult));

            if (!passwordsIsMatch) {
                model.addAttribute("passwordRepeatError", "Пароли не совпадают");
            }

            if (clientExists) {
                model.addAttribute("userExistsError", "Пользователь с таким логином уже существует");
            }

            model.addAttribute("client", client);
            return "registration";
        }

        clientService.save(client);
        return "redirect:/login";
    }
}
