package com.shop.GoodsShop;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@EnableEncryptableProperties
public class GoodsShopApplication implements WebMvcConfigurer {
	@Value("${uploadPath}")
	private String uploadPath;

	public static void main(String[] args) {
		SpringApplication.run(GoodsShopApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file://" + uploadPath);
	}
}