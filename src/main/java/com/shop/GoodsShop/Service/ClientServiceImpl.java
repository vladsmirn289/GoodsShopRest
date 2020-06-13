package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepo clientRepo;

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public void save(Client client) {
        clientRepo.save(client);
    }

    @Override
    public void delete(Client client) {
        clientRepo.delete(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepo.deleteById(id);
    }
}
