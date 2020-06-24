package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoItemException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Repositories.ItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private ItemRepo itemRepo;

    private Category book;
    private Item item;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        this.book = new Category("Book", books);
        this.item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        item.setId(1L);
    }

    @Test
    public void shouldFindByName() {
        Mockito
                .doReturn(Collections.singletonList(item))
                .when(itemRepo)
                .findByName("item");

        Item item = itemService.findByName("item").get(0);

        assertThat(item.getName()).isEqualTo("item");
        Mockito.verify(itemRepo, Mockito.times(1))
                .findByName("item");
    }

    @Test
    public void shouldFindByPrice() {
        Mockito
                .doReturn(Collections.singletonList(item))
                .when(itemRepo)
                .findByPrice(600D);

        Item item = itemService.findByPrice(600D).get(0);

        assertThat(item.getPrice()).isEqualTo(600D);
        Mockito.verify(itemRepo, Mockito.times(1))
                .findByPrice(600D);
    }

    @Test
    public void shouldFindByCategory() {
        Mockito
                .doReturn(Collections.singletonList(item))
                .when(itemRepo)
                .findByCategory(book);

        Item item = itemService.findByCategory(book).get(0);

        assertThat(item.getCategory().getName()).isEqualTo("Book");
        Mockito.verify(itemRepo, Mockito.times(1))
                .findByCategory(book);
    }

    @Test
    public void shouldFindById() {
        Mockito
                .doReturn(Optional.of(item))
                .when(itemRepo)
                .findById(1L);

        Item item = itemService.findById(1L);

        assertThat(item.getId()).isEqualTo(1L);
        Mockito.verify(itemRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldRaiseExceptionWhenFindByUnknownId() {
        assertThrows(NoItemException.class,
                () -> itemService.findById(1L));

        Mockito.verify(itemRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldFindByCode() {
        Mockito
                .doReturn(item)
                .when(itemRepo)
                .findByCode("123");

        Item item = itemService.findByCode("123");

        assertThat(item.getCode()).isEqualTo("123");
        Mockito.verify(itemRepo, Mockito.times(1))
                .findByCode("123");
    }

    @Test
    public void shouldSaveItem() {
        itemService.save(item);

        Mockito.verify(itemRepo, Mockito.times(1))
                .save(item);
    }

    @Test
    public void shouldDeleteItem() {
        itemService.delete(item);

        Mockito.verify(itemRepo, Mockito.times(1))
                .delete(item);
    }

    @Test
    public void shouldDeleteItemById() {
        itemService.deleteById(1L);

        Mockito.verify(itemRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
