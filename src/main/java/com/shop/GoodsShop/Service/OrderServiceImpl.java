package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import com.shop.GoodsShop.Repositories.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private OrderRepo orderRepo;

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo) {
        logger.debug("Setting orderRepo");
        this.orderRepo = orderRepo;
    }

    @Override
    public void save(Order order) {
        logger.info("Saving order with id = " + order.getId() + " to database");
        orderRepo.save(order);
    }

    @Override
    public void delete(Order order) {
        logger.info("Deleting order with id = " + order.getId() + " from database");
        orderRepo.delete(order);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting order with id = " + id + " from database");
        orderRepo.deleteById(id);
    }
}
