package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private ClientRepo clientRepo;

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        logger.debug("Setting clientRepo");
        this.clientRepo = clientRepo;
    }

    @Override
    public void save(Client client) {
        logger.info("Saving client with first name = " + client.getFirstName() + "" +
                ", last name " + client.getLastName() + " to database");
        clientRepo.save(client);
    }

    @Override
    public void delete(Client client) {
        logger.info("Deleting client with first name = " + client.getFirstName() + "" +
                ", last name " + client.getLastName() + " from database");
        clientRepo.delete(client);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting client with id = " + id + " from database");
        clientRepo.deleteById(id);
    }
}
