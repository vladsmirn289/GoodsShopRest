package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private ItemRepo itemRepo;

    @Autowired
    public void setItemRepo(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public void save(Item item) {
        itemRepo.save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepo.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        itemRepo.deleteById(id);
    }
}
