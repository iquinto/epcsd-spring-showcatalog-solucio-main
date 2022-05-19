package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class ShowUnitTest {

    @Test
    public void createAndCancelShowTest() {
        Category category = new Category(1L, "music", "Music Category");

        Performance performance = new Performance(LocalDate.of(2022,6,16), LocalTime.of(2,30), "www.youtube.com", 56L, Status.CREATED);

        Set<Performance> performances = new HashSet<>();
        performances.add(performance);
        Show show = new Show(1L,  "U2 Concert", "Some description", "u2.png",  45.00, 2.00, 5000L, LocalDate.of(2022,6,16), category, performances);

        assertThat(show).isNotNull();
        assertThat(show.getCategory()).isEqualTo(category);
        assertThat(show.getStatus()).isEqualTo(Status.CREATED);

    }

}
