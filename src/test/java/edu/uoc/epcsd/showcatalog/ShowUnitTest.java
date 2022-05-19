package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

class ShowUnitTest {

    @Test
    public void createAndCancelShowTest() {
        Category category = new Category(1L, "music", "Music Category");

        Performance performance = new Performance(LocalDate.of(2022,06,16), );

        Show show = new Show(1L, category, "U2 Concert", "Some description", "u2.png",
                45.00, 2.00, 5000.00, LocalDate.of(2022,06,16));

        assertThat(show).isNotNull();
        assertThat(show.getCategory()).isEqualTo(category);
        assertThat(show.getStatus()).isEqualTo(Status.CREATED);

    }

}
