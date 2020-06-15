package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.OrderedItem;
import com.shop.GoodsShop.Repositories.OrderedItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedItemServiceImpl implements OrderedItemService {
    private OrderedItemRepo orderedItemRepo;

    @Autowired
    public void setOrderedItemRepo(OrderedItemRepo orderedItemRepo) {
        this.orderedItemRepo = orderedItemRepo;
    }

    @Override
    public void save(OrderedItem order) {
        orderedItemRepo.save(order);
    }

    @Override
    public void delete(OrderedItem order) {
        orderedItemRepo.delete(order);
    }

    @Override
    public void deleteById(Long id) {
        orderedItemRepo.deleteById(id);
    }
}
