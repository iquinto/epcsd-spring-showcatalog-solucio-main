package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class ShowUnitTest {

    @DisplayName("test [unit] create and cancel show")
    @Test
    public void test_create_and_cancel_show() {

        //given
        Category category = new Category(1L, "music", "Music Category");
        Performance performance = new Performance(LocalDate.of(2022,6,16), LocalTime.of(2,30), "www.youtube.com", 56L, Status.CREATED);

        //when
        Set<Performance> performances = new HashSet<>();
        performances.add(performance);
        Show show = new Show();
        show.setId(1L);
        show.setCategory(category);
        show.setName("U2 Tour");
        show.setDescription("This is U2");
        show.setImage("u2.jpeg");
        show.setPrice(150.0);
        show.setDuration(190.0);
        show.setOnSaleDate(LocalDate.of(2022,6,16));
        show.setPerformances(performances);

        //then
        assertThat(show).isNotNull();
        assertThat(show.getCategory()).isEqualTo(category);
        assertThat(show.getStatus()).isEqualTo(Status.CREATED);

        //when
        show.cancel();

        //then:
        assertThat(show.getStatus()).isEqualTo(Status.CANCELLED);
        assertThat(performance.getStatus()).isEqualTo(Status.CANCELLED);
        
    }

}
