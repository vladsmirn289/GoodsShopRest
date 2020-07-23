package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Exception.NoCategoryException;
import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Repositories.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

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
    public void shouldFindCategoryByName() {
        Category category = new Category("ABC");
        Mockito
                .doReturn(category)
                .when(categoryRepo)
                .findByName("ABC");

        Category category1 = categoryService.findByName("ABC");

        assertThat(category1).isNotNull();
        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByName("ABC");
    }

    @Test
    public void shouldGetAllNamesOfCategories() {
        Category books = new Category("Books");
        Category electronics = new Category("Electronics");
        List<Category> categories = new ArrayList<>(Arrays.asList(books, electronics));

        Mockito
                .doReturn(categories)
                .when(categoryRepo)
                .findByParentIsNull();

        Set<String> names = categoryService.getAllNamesOfCategories();

        assertThat(names).isNotNull();
        assertThat(names).contains("Books", "Electronics");

        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByParentIsNull();
    }

    @Test
    public void shouldGetAllNamesOfChildren() {
        Category books = new Category("Books");
        Category book1 = new Category("Book1", books);
        Category book2 = new Category("Book2", books);
        List<Category> parent = new ArrayList<>(Collections.singletonList(books));
        List<Category> children = new ArrayList<>(Arrays.asList(book1, book2));

        Mockito
                .doReturn(parent)
                .when(categoryRepo)
                .findByParentIsNull();

        Mockito
                .doReturn(children)
                .when(categoryRepo)
                .findByParent(books);

        Set<String> names = categoryService.getAllNamesOfChildren();

        assertThat(names).isNotNull();
        assertThat(names).contains("Book1", "Book2");

        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByParentIsNull();
        Mockito.verify(categoryRepo, Mockito.times(1))
                .findByParent(books);
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
