package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CatalogServiceUnitTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private CatalogServiceImpl catalogService;

    private Show show;

    private Set<Performance> performances;

    private Category category;


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

    @DisplayName("test [unit] findShowById method correctly")
    @Test
    public void test_find_show_by_id_correctly() {
        // given
        Mockito.when(showRepository.findShowById(show.getId())).thenReturn(Optional.ofNullable(show));

        // when
        Optional<Show> showInstance = catalogService.findShowById(show.getId());

        // then
        assertThat(showInstance).isNotNull();
        assertThat(showInstance.get()).isExactlyInstanceOf(Show.class);
        assertThat(showInstance.get().getId()).isEqualTo(show.getId());
        assertThat(showInstance.get().getStatus()).isEqualTo(Status.CREATED);
    }

    @DisplayName("test [unit] findShowById method incorrectly")
    @Test
    public void test_find_show_by_id_incorrectly() {
        // given
        Mockito.when(showRepository.findShowById(0L)).thenReturn(null);

        // when
        Optional<Show> showInstance = catalogService.findShowById(0L);

        // then
        assertThat(showInstance).isNull();
    }

}
