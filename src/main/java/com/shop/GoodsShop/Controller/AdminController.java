package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.Role;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Utils.URIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ClientService clientService;
    private CategoryService categoryService;

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAdminFunctions() {
        logger.info("Called showAdminFunctions method");

        return "admin/adminFunctions";
    }

    @GetMapping("/listOfUsers")
    public String showListUsers(Model model) {
        logger.info("Called showListUsers method");
        List<Client> clients = clientService.findAll();
        model.addAttribute("users", clients);

        return "admin/listOfUsers";
    }

    @GetMapping("/addManager/{id}")
    public String addManager(@PathVariable("id") Long userId,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer) {
        logger.info("Called addManager method");
        Client client = clientService.findById(userId);

        Set<Role> roles = client.getRoles();
        roles.add(Role.MANAGER);
        client.setRoles(roles);
        clientService.save(client);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/addAdmin/{id}")
    public String addAdmin(@PathVariable("id") Long userId,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(required = false) String referer) {
        logger.info("Called addAdmin method");
        Client client = clientService.findById(userId);

        Set<Role> roles = client.getRoles();
        roles.add(Role.ADMIN);
        client.setRoles(roles);
        clientService.save(client);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/removeManager/{id}")
    public String removeManager(@PathVariable("id") Long userId,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(required = false) String referer) {
        logger.info("Called removeManager method");
        Client client = clientService.findById(userId);

        Set<Role> roles = client.getRoles();
        roles.remove(Role.MANAGER);
        client.setRoles(roles);
        clientService.save(client);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/lockAccount/{id}")
    public String lockAccount(@PathVariable("id") Long userId,
                              RedirectAttributes redirectAttributes,
                              @RequestHeader(required = false) String referer) {
        logger.info("Called lockAccount method");
        Client client = clientService.findById(userId);
        client.setNonLocked(false);
        clientService.save(client);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/unlockAccount/{id}")
    public String unlockAccount(@PathVariable("id") Long userId,
                                RedirectAttributes redirectAttributes,
                                @RequestHeader(required = false) String referer) {
        logger.info("Called unlockAccount method");
        Client client = clientService.findById(userId);
        client.setNonLocked(true);
        clientService.save(client);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/createCategory")
    public String createCategoryPage(Model model) {
        logger.info("Called createCategoryPage method");
        model.addAttribute("parents", categoryService.getAllNamesOfCategories());

        return "admin/createCategory";
    }

    @PostMapping("/createCategory")
    public String createNewCategory(@RequestParam("category") String parent,
                                    @RequestParam("name") String name,
                                    Model model) {
        logger.info("Called createNewCategory");
        if (name.trim().equals("")) {
            model.addAttribute("nameError", "Имя категории не может быть пустым");
            model.addAttribute("name", name);
            model.addAttribute("parents", categoryService.getAllNamesOfCategories());
            return "admin/createCategory";
        } else if (categoryService.findByName(name) != null) {
            model.addAttribute("nameError", "Категория с таким именем уже существует");
            model.addAttribute("name", name);
            model.addAttribute("parents", categoryService.getAllNamesOfCategories());
            return "admin/createCategory";
        }

        Category newCategory = new Category(name);

        if (!parent.equals("Родительская категория")) {
            Category parentCategory = categoryService.findByName(parent);
            newCategory.setParent(parentCategory);
        }

        categoryService.save(newCategory);

        return "messages/successfulCreatedCategory";
    }
}
