package com.challenge.stock.repository;

import com.challenge.stock.domain.Drink;
import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, String> {
    List<Section> findAllByType(Category type);
}
