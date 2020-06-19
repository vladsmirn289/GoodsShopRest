package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoItemException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private ItemRepo itemRepo;

    @Autowired
    public void setItemRepo(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByName(String name) {
        return itemRepo.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByPrice(Double price) {
        return itemRepo.findByPrice(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByCategory(Category category) {
        return itemRepo.findByCategory(category);
    }

    @Override
    public Item findById(Long id) {
        return itemRepo.findById(id).orElseThrow(NoItemException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findByCode(String code) {
        return itemRepo.findByCode(code);
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
