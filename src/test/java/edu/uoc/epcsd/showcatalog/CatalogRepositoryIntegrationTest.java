package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CatalogRepositoryIntegrationTest {

    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("test [integration] findCategoryById method")
    @Test
    void test_find_category_by_id() {
        // given
        Category c =  new Category();
        c.setName("new category");
        c.setName("some description");
        Long newCategoryId  = categoryRepository.createCategory(c);

        // when
        Optional<Category> category = categoryRepository.findCategoryById(newCategoryId);

        // then
        assertThat(category).isNotNull();
        assertThat(category.get()).isExactlyInstanceOf(Category.class);
        assertThat(category.get().getId()).isEqualTo(newCategoryId);
        assertThat(category.get().getName()).isEqualTo(c.getName());

    }

}
