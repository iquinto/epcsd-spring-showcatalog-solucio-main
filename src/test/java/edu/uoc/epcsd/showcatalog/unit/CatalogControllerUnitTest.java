package edu.uoc.epcsd.showcatalog.unit;

import edu.uoc.epcsd.showcatalog.application.rest.CatalogRESTController;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogServiceImpl;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCategoryRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataShowRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CatalogRESTController.class)
class CatalogControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SpringDataCategoryRepository springDataCategoryRepository;

    @MockBean
    SpringDataShowRepository springDataShowRepository;

    @MockBean
    private CatalogServiceImpl catalogService;

    private Show show;

    private Category category;

    private Set<Performance> performances;

    @BeforeEach
    public void setup(){
        category = new Category(1L, "music", "Music Category");
        performances = new HashSet<>();
        performances.add(new Performance(LocalDate.of(2022,6,16),
                LocalTime.of(2,30), "www.youtube.com",
                56L, Status.CREATED));
        show = new Show();
        show.setId(1L);
        show.setCategory(category);
        show.setName("U2 Tour");
        show.setDescription("This is U2");
        show.setImage("u2.jpeg");
        show.setPrice(150.0);
        show.setDuration(190.0);
        show.setOnSaleDate(LocalDate.of(2022,6,16));
        show.setPerformances(performances);
    }

    @DisplayName("test find all categories with WebMvcTest")
    @Test
    public void testFindCategories() throws Exception {

        //given:
        Mockito.when(catalogService.findAllCategories()).thenReturn(List.of(category));

        // when
        ResultActions response = mockMvc.perform(get("/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        response.andExpect(status().isOk());
        response.andExpect(handler().handlerType(CatalogRESTController.class));
        response.andExpect(handler().methodName("findCategories"));
        response.andExpect(jsonPath("$", hasSize(1)));
        response.andExpect(jsonPath("$[0].name", Matchers.is(category.getName())));
    }

}
