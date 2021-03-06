package edu.uoc.epcsd.showcatalog;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
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
        category = Category.builder().name("MUSIC_SHOWS").build();;
        show = Show.builder().name("Kiss - The final tour").category(category).capacity(45000L).build();
    }

    @DisplayName("test [unit]  /categories endpoint with WebMvcTest")
    @Test
    public void test_find_categories() throws Exception {

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
