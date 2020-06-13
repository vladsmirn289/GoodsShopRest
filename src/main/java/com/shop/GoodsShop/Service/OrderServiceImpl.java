package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import com.shop.GoodsShop.Repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public void save(Order order) {
        orderRepo.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepo.delete(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }
}
