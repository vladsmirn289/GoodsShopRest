package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoItemException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Repositories.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private ItemRepo itemRepo;

    @Autowired
    public void setItemRepo(ItemRepo itemRepo) {
        logger.debug("Setting itemRepo");
        this.itemRepo = itemRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByName(String name) {
        logger.info("findByName method called for item name = " + name);
        return itemRepo.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByPrice(Double price) {
        logger.info("findByPrice method called for item price = " + price);
        return itemRepo.findByPrice(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByCategory(Category category) {
        logger.info("findByCategory method called for category name = " + category.getName());
        return itemRepo.findByCategory(category);
    }

    @Override
    public Item findById(Long id) {
        logger.info("findById method called for item id = " + id);
        return itemRepo.findById(id).orElseThrow(NoItemException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findByCode(String code) {
        logger.info("findByCode method called for item code = " + code);
        return itemRepo.findByCode(code);
    }

    @Override
    public void save(Item item) {
        logger.info("Saving item with name = " + item.getName() + " to database");
        itemRepo.save(item);
    }

    @Override
    public void delete(Item item) {
        logger.info("Deleting item with name = " + item.getName() + " from database");
        itemRepo.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting item with id = " + id + " from database");
        itemRepo.deleteById(id);
    }
}
