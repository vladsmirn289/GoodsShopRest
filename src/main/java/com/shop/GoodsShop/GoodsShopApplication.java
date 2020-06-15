package com.shop.GoodsShop;

import com.shop.GoodsShop.Service.InitDB;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GoodsShopApplication implements InitializingBean {
	private InitDB initDB;

	@Autowired
	public void setInitDB(InitDB initDB) {
		this.initDB = initDB;
	}

	public static void main(String[] args) {
		SpringApplication.run(GoodsShopApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		initDB.init();
	}
}
