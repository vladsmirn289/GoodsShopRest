package com.shop.GoodsShop;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@EnableEncryptableProperties
public class GoodsShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoodsShopApplication.class, args);
	}
}