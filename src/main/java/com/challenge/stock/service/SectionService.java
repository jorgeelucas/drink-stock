package com.challenge.stock.service;

import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
import com.challenge.stock.dto.TotalsSectionDTO;
import com.challenge.stock.repository.SectionRepository;
import com.challenge.stock.service.exception.InvalidCategoryException;
import com.challenge.stock.service.exception.DrinkTypeInvalidException;
import com.challenge.stock.service.exception.InsufficientCapacityException;
import com.challenge.stock.service.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section save(Section section) {
        log.info("Saving a new section, status=start, section={} ", section);
        Section saved = this.sectionRepository.save(section);
        log.info("new section saved successfully, status=end, id={} ", saved.getId());
        return saved;
    }

    public Section findById(String id) {
        log.info("Finding a section by id, status=start, id={} ", id);
        Optional<Section> section = this.sectionRepository.findById(id);

        if (section.isEmpty()) {
            log.info("Finding a section by id, status=error, msg=Section not found: {} ", id);
            throw new ObjectNotFoundException("Section not found: " + id);
        }
        log.info("Finding a section by id successfully, status=end, section={} ", section.get());
        return section.get();
    }

    public void bookDrink(String sectionId, Long quantity, Category type) {
        log.info("Booking a new drink, status=start, sectionid={}, quantity={}, category={} ", sectionId, quantity, type.name());
        Section section = findById(sectionId);
        validateCapacity(section, quantity);
        validateType(section, type);
        section.setFillment(section.getFillment() + quantity);
        save(section);
        log.info("Booking a new drink successfully, status=end, new section fillment={} ", section.getFillment());
    }

    public TotalsSectionDTO findTotalForType(String type) {
        log.info("Find totals for type, status=start, type={} ", type);
        Category category = getCategoryFromStringType(type);
        List<Section> sections = sectionRepository.findAllByType(category);
        TotalsSectionDTO dto = new TotalsSectionDTO();
        dto.setType(category.name());
        dto.setAmount(calculateAmount(sections));
        log.info("Find totals for type successfully, status=end, response={} ", dto);
        return dto;
    }

    public Long findTotalForSection(String id) {
        log.info("Finding total for section id, status=start, id={} ", id);
        Section section = findById(id);
        log.info("Finding total for section id successfully, status=end, response={} ", section.getFillment());
        return section.getFillment();
    }

    public List<Section> availableSectionsForAmount(Long amount, String type) {
        log.info("Finding available sections, status=start, amount={}, category={} ", amount, type);
        // obs.: calcular via algoritmo
        Category category = getCategoryFromStringType(type);

        List<Section> sections = sectionRepository.findAllByType(category);

        List<Section> filtered = sections.stream().filter(section -> amount == null || ((section.getCapacity() - section.getFillment())) >= amount)
                .collect(Collectors.toList());

        log.info("Finding available sections successfully, status=end, list={} ", filtered);
        return filtered;
    }

    private Long calculateAmount(List<Section> sections) {
        log.info("Calculate amount of sections list");
        return sections.stream()
                .reduce(0L, (subtotal, element) -> subtotal + element.getFillment(), Long::sum);
    }

    private void validateType(Section section, Category type) {
        log.info("Validating type of new drink to the section");
        if (!section.getType().equals(type)) {
            log.info("Validating type of new drink to the section, status=error, msg=Invalid type");
            throw new DrinkTypeInvalidException("Invalid type");
        }
    }

    private void validateCapacity(Section section, Long quantity) {
        log.info("Validating section capacity, status=start");
        Long availableCapacity = section.getCapacity() - section.getFillment();
        if (availableCapacity < quantity) {
            log.info("Validating section capacity, status=error, msg=Insufficient capacity");
            throw new InsufficientCapacityException("Insufficient capacity");
        }
        log.info("Validating section capacity successfully, status=end");
    }

    private Category getCategoryFromStringType(String type) {
        log.info("Getting category for string type, status=start, type={} ", type);
        if (StringUtils.isNotBlank(type) && Category.isMember(type)) {
            String upperType = type.toUpperCase();
            Category category = Category.valueOf(upperType);
            log.info("Getting category for string type successfully, status=end, category={} ", category);
            return category;
        }
        log.info("Getting category for string type, status=error, msg= Invalid category: {}", type);
        throw new InvalidCategoryException("Invalid category: " + type);
    }

    public List<Section> getAllSections() {
        log.info("Getting all sections, status=start");
        List<Section> list = sectionRepository.findAll();
        log.info("Getting all sections, status=end, resp={}", list);
        return list;
    }
}
