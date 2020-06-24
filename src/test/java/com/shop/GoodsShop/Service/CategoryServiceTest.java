package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Excepton.NoCategoryException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Repositories.CategoryRepo;
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
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private CategoryRepo categoryRepo;

    @Test
    public void shouldFindByParent() {
        Category parent = new Category("Parent");
        Category child = new Category("Child", parent);

        Mockito
                .doReturn(Collections.singletonList(child))
                .when(categoryRepo)
                .findByParent(parent);

        Category category = categoryService.findByParent(parent).get(0);

        assertThat(category.getName()).isEqualTo("Child");
        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByParent(parent);
    }

    @Test
    public void shouldFindByParentIsNull() {
        Category parent = new Category("Parent");

        Mockito
                .doReturn(Collections.singletonList(parent))
                .when(categoryRepo)
                .findByParentIsNull();

        Category category = categoryService.findByParentIsNull().get(0);

        assertThat(category.getName()).isEqualTo("Parent");
        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByParentIsNull();
    }

    @Test
    public void shouldFindById() {
        Category parent = new Category("Parent");
        parent.setId(1L);

        Mockito
                .doReturn(Optional.of(parent))
                .when(categoryRepo)
                .findById(1L);

        Category category = categoryService.findById(1L);

        assertThat(category.getId()).isEqualTo(1L);
        Mockito.verify(categoryRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldRaiseExceptionWhenFindByUnknownId() {
        assertThrows(NoCategoryException.class,
                () -> categoryService.findById(1L));

        Mockito.verify(categoryRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldSaveCategory() {
        Category category = new Category("Hello world");
        categoryService.save(category);

        Mockito.verify(categoryRepo, Mockito.times(1))
                .save(category);
    }

    @Test
    public void shouldDeleteCategory() {
        categoryService.delete(new Category("Parent"));

        Mockito.verify(categoryRepo, Mockito.times(1))
                .delete(new Category("Parent"));
    }

    @Test
    public void shouldDeleteCategoryById() {
        categoryService.deleteById(1L);

        Mockito.verify(categoryRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
