package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Model.Role;
import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Utils.FileUtil;
import com.shop.GoodsShop.Utils.URIUtils;
import com.shop.GoodsShop.Utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ClientService clientService;
    private CategoryService categoryService;
    private ItemService itemService;
    private final Validator validator;

    @Value("${uploadPath}")
    private String uploadPath;

    public AdminController() {
        logger.debug("Called constructor of AdminController class");
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        this.validator = validatorFactoryBean;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        logger.debug("Setting clientService");
        this.clientService = clientService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        logger.debug("Setting categoryService");
        this.categoryService = categoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        logger.debug("Setting itemService");
        this.itemService = itemService;
    }

    @GetMapping
    public String showAdminFunctions() {
        logger.info("Called showAdminFunctions method");

        return "admin/adminFunctions";
    }

    @GetMapping("/listOfUsers")
    public String showListUsers(Model model,
                                @PageableDefault(sort = {"firstName"}) Pageable pageable,
                                @CookieValue("jwtToken") String token) {
        logger.info("Called showListUsers method");
        Page<Client> clients = clientService.findAll(pageable, token);
        model.addAttribute("url", "/admin/listOfUsers?");
        model.addAttribute("users", clients);

        return "admin/listOfUsers";
    }

    @GetMapping("/addManager/{id}")
    public String addManager(@PathVariable("id") Long userId,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer,
                             @CookieValue("jwtToken") String token) {
        logger.info("Called addManager method");
        Client client = clientService.findById(userId, token);

        Set<Role> roles = client.getRoles();
        roles.add(Role.MANAGER);
        client.setRoles(roles);
        clientService.save(client, token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/addAdmin/{id}")
    public String addAdmin(@PathVariable("id") Long userId,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(required = false) String referer,
                           @CookieValue("jwtToken") String token) {
        logger.info("Called addAdmin method");
        Client client = clientService.findById(userId, token);

        Set<Role> roles = client.getRoles();
        roles.add(Role.ADMIN);
        client.setRoles(roles);
        clientService.save(client, token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/removeManager/{id}")
    public String removeManager(@PathVariable("id") Long userId,
                                RedirectAttributes redirectAttributes,
                                @RequestHeader(required = false) String referer,
                                @CookieValue("jwtToken") String token) {
        logger.info("Called removeManager method");
        Client client = clientService.findById(userId, token);

        Set<Role> roles = client.getRoles();
        roles.remove(Role.MANAGER);
        client.setRoles(roles);
        clientService.save(client, token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/lockAccount/{id}")
    public String lockAccount(@PathVariable("id") Long userId,
                              RedirectAttributes redirectAttributes,
                              @RequestHeader(required = false) String referer,
                              @CookieValue("jwtToken") String token) {
        logger.info("Called lockAccount method");
        Client client = clientService.findById(userId, token);
        client.setNonLocked(false);
        clientService.save(client, token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/unlockAccount/{id}")
    public String unlockAccount(@PathVariable("id") Long userId,
                                RedirectAttributes redirectAttributes,
                                @RequestHeader(required = false) String referer,
                                @CookieValue("jwtToken") String token) {
        logger.info("Called unlockAccount method");
        Client client = clientService.findById(userId, token);
        client.setNonLocked(true);
        clientService.save(client, token);

        return "redirect:" + URIUtils.toPriorPage(referer, redirectAttributes);
    }

    @GetMapping("/createCategory")
    public String createCategoryPage(Model model) {
        logger.info("Called createCategoryPage method");
        model.addAttribute("parents", categoryService.getAllNamesOfRootCategories());

        return "admin/createCategory";
    }

    @PostMapping("/createCategory")
    public String createNewCategory(@RequestParam("category") String parent,
                                    @RequestParam("name") String name,
                                    Model model) {
        logger.info("Called createNewCategory");

        Category foundByName = categoryService.findByName(name);
        if (name.trim().equals("") || foundByName != null) {
            if (foundByName != null) {
                logger.error("Category with this name is already exists");
                model.addAttribute("nameError", "Категория с таким именем уже существует");
            } else {
                logger.error("Name is blank");
                model.addAttribute("nameError", "Имя категории не может быть пустым");
            }

            model.addAttribute("name", name);
            model.addAttribute("parents", categoryService.getAllNamesOfRootCategories());
            return "admin/createCategory";
        }

        Category newCategory = new Category(name);

        if (!parent.equals("Родительская категория")) {
            logger.debug("Chosen category is not parent");
            Category parentCategory = categoryService.findByName(parent);
            newCategory.setParent(parentCategory);
        }

        categoryService.save(newCategory);

        return "messages/successfulCreatedCategory";
    }

    @GetMapping("/createItem")
    public String createItemPage(Model model) {
        logger.info("Called createItemPage method");
        List<String> categories = categoryService.getAllNamesOfChildren();
        model.addAttribute("categories", categories);

        return "admin/createItem";
    }

    @GetMapping("/updateItem/{id}")
    public String updateItemPage(@PathVariable("id") Long itemId,
                                 Model model) {
        logger.info("Called updateItemPage method");
        List<String> categories = categoryService.getAllNamesOfChildren();
        model.addAttribute("categories", categories);
        model.addAttribute("item", itemService.findById(itemId));

        return "admin/createItem";
    }

    @PostMapping("/createOrUpdateItem")
    public String createOrUpdateItem(@ModelAttribute("newItem") Item item,
                                     @RequestParam("fileImage") MultipartFile file,
                                     @RequestParam("categoryName") String categoryName,
                                     Model model) throws IOException {
        logger.info("Called createOrUpdateItem method");
        if (item.getId() == null) {
            item.setCode(UUID.randomUUID().toString());
        }

        item.setCategory(categoryService.findByName(categoryName));

        FileUtil fileUtil = new FileUtil();
        boolean isExtWrong = fileUtil.hasInvalidExtension(file.getOriginalFilename());
        if (isExtWrong) {
            logger.error("Invalid extension of file");
            model.addAttribute("fileExtError", "Файл должен быть изображением");
        }

        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        if (constraintViolations.size() != 0 || isExtWrong) {
            logger.warn("Create item form has errors!");
            Map<String, String> errors = ValidateUtil.validate(constraintViolations);
            model.mergeAttributes(errors);
            model.addAttribute("item", item);
            model.addAttribute("categories", categoryService.getAllNamesOfChildren());
            return "admin/createItem";
        }

        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + filename));
        item.setImage(filename);

        Long id = item.getId();
        itemService.save(item);

        if (id == null) {
            logger.info("Item successful created");
            return "messages/successfulItemCreated";
        } else {
            logger.info("Item successful updated");
            return "messages/successfulItemUpdated";
        }
    }
}
