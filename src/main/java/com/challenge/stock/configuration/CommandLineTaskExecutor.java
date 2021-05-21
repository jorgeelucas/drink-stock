package com.challenge.stock.configuration;

import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
import com.challenge.stock.dto.BookDTO;
import com.challenge.stock.repository.SectionRepository;
import com.challenge.stock.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Profile("!test")
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DrinkService drinkService;

    @Override
    public void run(String... args) throws Exception {
        Section section = new Section();
        section.setId(UUID.randomUUID().toString());
        section.setName("First section");
        section.setFillment(0L);
        section.setCapacity(Category.ALCOHOLIC.getCapacity());
        section.setType(Category.ALCOHOLIC);

        Section section2 = new Section();
        section2.setId(UUID.randomUUID().toString());
        section2.setName("Second section");
        section2.setFillment(0L);
        section2.setCapacity(Category.NON_ALCOHOLIC.getCapacity());
        section2.setType(Category.NON_ALCOHOLIC);

        Section section3 = new Section();
        section3.setId(UUID.randomUUID().toString());
        section3.setName("Third section");
        section3.setFillment(0L);
        section3.setCapacity(Category.ALCOHOLIC.getCapacity());
        section3.setType(Category.ALCOHOLIC);

        Section section4 = new Section();
        section4.setId(UUID.randomUUID().toString());
        section4.setName("Fourth section");
        section4.setFillment(0L);
        section4.setCapacity(Category.NON_ALCOHOLIC.getCapacity());
        section4.setType(Category.NON_ALCOHOLIC);

        Section section5 = new Section();
        section5.setId(UUID.randomUUID().toString());
        section5.setName("Fifth section");
        section5.setFillment(0L);
        section5.setCapacity(Category.ALCOHOLIC.getCapacity());
        section5.setType(Category.ALCOHOLIC);

        List<Section> sectionList = this.sectionRepository.saveAll(List.of(section, section2, section3, section4, section5));
    }

}
