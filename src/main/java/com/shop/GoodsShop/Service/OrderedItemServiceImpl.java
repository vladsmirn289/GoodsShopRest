package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.OrderedItem;
import com.shop.GoodsShop.Repositories.OrderedItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedItemServiceImpl implements OrderedItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private OrderedItemRepo orderedItemRepo;

    @Autowired
    public void setOrderedItemRepo(OrderedItemRepo orderedItemRepo) {
        logger.debug("Setting orderedItemRepo");
        this.orderedItemRepo = orderedItemRepo;
    }

    @Override
    public void save(OrderedItem orderedItem) {
        logger.info("Saving ordered item with item name = " + orderedItem.getItem().getName() + " to database");
        orderedItemRepo.save(orderedItem);
    }

    @Override
    public void delete(OrderedItem orderedItem) {
        logger.info("Deleting ordered item with item name = " + orderedItem.getItem().getName() + " from database");
        orderedItemRepo.delete(orderedItem);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Saving ordered item with id = " + id + " from database");
        orderedItemRepo.deleteById(id);
    }
}
