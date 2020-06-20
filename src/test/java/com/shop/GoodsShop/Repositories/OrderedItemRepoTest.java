package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Model.OrderedItem;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderedItemRepoTest {
    @Autowired
    private OrderedItemRepo orderedItemRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @MockBean
    private InitDB initDB;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        OrderedItem orderedItem = new OrderedItem(item, 2);

        categoryRepo.save(books);
        categoryRepo.save(book);
        itemRepo.save(item);
        orderedItemRepo.save(orderedItem);
    }

    @AfterEach
    public void resetSequence() {
        entityManager.getEntityManager()
                .createNativeQuery("alter sequence hibernate_sequence restart 1")
                .executeUpdate();
    }

    @Test
    public void shouldFindOrderedItemById() {
        OrderedItem orderedItem = orderedItemRepo.findById(4L).orElse(null);

        assertThat(orderedItem).isNotNull();
        assertThat(orderedItem.getItem().getName()).isEqualTo("item");
        assertThat(orderedItem.getQuantity()).isEqualTo(2);
    }

    @Test
    public void shouldSaveOrderedItem() {
        Category laptops = new Category("Laptops");
        Category laptop = new Category("Laptop", laptops);
        Item item = new Item("laptop", 40L, 2D
                , 56000D, "LaptopDescription..."
                , "LaptopCharacteristics...", "567", laptop);
        OrderedItem orderedItem = new OrderedItem(item, 1);

        categoryRepo.save(laptops);
        categoryRepo.save(laptop);
        itemRepo.save(item);
        orderedItemRepo.save(orderedItem);

        assertThat(itemRepo.findAll().size()).isEqualTo(2);
        OrderedItem orderedItem1 = orderedItemRepo.findById(8L).orElse(null);

        assertThat(orderedItem1).isNotNull();
        assertThat(orderedItem1.getItem().getName()).isEqualTo("laptop");
        assertThat(orderedItem1.getQuantity()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteOrderedItem() {
        OrderedItem orderedItem = orderedItemRepo.findById(4L).orElse(null);
        assertThat(orderedItem).isNotNull();
        orderedItemRepo.delete(orderedItem);

        assertThat(orderedItemRepo.findAll().size()).isEqualTo(0);
    }

    @Test
    public void shouldDeleteOrderedItemById() {
        orderedItemRepo.deleteById(4L);

        assertThat(orderedItemRepo.findAll().size()).isEqualTo(0);
    }
}
