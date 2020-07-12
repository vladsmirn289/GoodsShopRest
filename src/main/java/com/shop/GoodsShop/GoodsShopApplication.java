package com.shop.GoodsShop;

import com.shop.GoodsShop.Service.InitDB;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@EnableEncryptableProperties
public class GoodsShopApplication implements InitializingBean {
	@Value("${init}")
	private boolean needInit;

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
		if(needInit) {
			initDB.init();
		}
	}
}