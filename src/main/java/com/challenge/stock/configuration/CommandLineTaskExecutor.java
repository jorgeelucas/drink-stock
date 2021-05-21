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


        BookDTO dto = new BookDTO();
        dto.setType(Category.ALCOHOLIC);
        dto.setCreatedBy("jorge");
        dto.setQuantity(22L);
        dto.setDrinkName("skol");
        dto.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto1 = new BookDTO();
        dto1.setType(Category.ALCOHOLIC);
        dto1.setCreatedBy("jorge");
        dto1.setQuantity(44L);
        dto1.setDrinkName("amstel");
        dto1.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto2 = new BookDTO();
        dto2.setType(Category.ALCOHOLIC);
        dto2.setCreatedBy("jorge");
        dto2.setQuantity(37L);
        dto2.setDrinkName("heineken");
        dto2.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto3 = new BookDTO();
        dto3.setType(Category.ALCOHOLIC);
        dto3.setCreatedBy("jorge");
        dto3.setQuantity(16L);
        dto3.setDrinkName("brahma");
        dto3.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto4 = new BookDTO();
        dto4.setType(Category.ALCOHOLIC);
        dto4.setCreatedBy("jorge");
        dto4.setQuantity(77L);
        dto4.setDrinkName("antartica");
        dto4.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto5 = new BookDTO();
        dto5.setType(Category.ALCOHOLIC);
        dto5.setCreatedBy("jorge");
        dto5.setQuantity(32L);
        dto5.setDrinkName("schin");
        dto5.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto6 = new BookDTO();
        dto6.setType(Category.ALCOHOLIC);
        dto6.setCreatedBy("jorge");
        dto6.setQuantity(62L);
        dto6.setDrinkName("Vodka");
        dto6.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto7 = new BookDTO();
        dto7.setType(Category.ALCOHOLIC);
        dto7.setCreatedBy("jorge");
        dto7.setQuantity(32L);
        dto7.setDrinkName("whisky");
        dto7.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.ALCOHOLIC)).findAny().get());

        BookDTO dto8 = new BookDTO();
        dto8.setType(Category.NON_ALCOHOLIC);
        dto8.setCreatedBy("jorge");
        dto8.setQuantity(43L);
        dto8.setDrinkName("coca");
        dto8.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.NON_ALCOHOLIC)).findAny().get());

        BookDTO dto9 = new BookDTO();
        dto9.setType(Category.NON_ALCOHOLIC);
        dto9.setCreatedBy("jorge");
        dto9.setQuantity(21L);
        dto9.setDrinkName("guarana");
        dto9.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.NON_ALCOHOLIC)).findAny().get());

        BookDTO dto10 = new BookDTO();
        dto10.setType(Category.NON_ALCOHOLIC);
        dto10.setCreatedBy("jorge");
        dto10.setQuantity(89L);
        dto10.setDrinkName("fanta");
        dto10.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.NON_ALCOHOLIC)).findAny().get());

        BookDTO dto11 = new BookDTO();
        dto11.setType(Category.NON_ALCOHOLIC);
        dto11.setCreatedBy("jorge");
        dto11.setQuantity(67L);
        dto11.setDrinkName("guarana jesus");
        dto11.setSection(sectionList.stream().filter(s -> s.getType().equals(Category.NON_ALCOHOLIC)).findAny().get());

        List<BookDTO> list = List.of(dto, dto11, dto2, dto3, dto4, dto5, dto1, dto6, dto10, dto7, dto9, dto8);

        list.stream().forEach(drink -> drinkService.book(drink));
    }

}
