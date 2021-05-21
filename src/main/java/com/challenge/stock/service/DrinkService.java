package com.challenge.stock.service;

import com.challenge.stock.domain.Drink;
import com.challenge.stock.dto.BookDTO;
import com.challenge.stock.repository.DrinkRepository;
import com.challenge.stock.service.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private SectionService sectionService;

    public void book(BookDTO dto) {
        log.info("Book Drink, status=start, request={} ", dto);
        sectionService.bookDrink(dto.getSection().getId(), dto.getQuantity(), dto.getType());
        Drink drink = new Drink(dto);
        Drink saved = drinkRepository.save(drink);
        log.info("Book Drink, status=end, drink={} ", drink);
    }

    public Drink findById(Integer id) {
        log.info("FindById, status=start, id={} ", id);
        Drink drink = this.drinkRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Drink not found: " + id));
        log.info("FindById, status=end, drink={} ", drink);
        return drink;
    }

    public Page<Drink> getPaged(Pageable pageable) {
        log.info("GetAllDrinksPaginated, status=start, pageable={} ", pageable);
        Page<Drink> drinksPage = drinkRepository.findAll(pageable);
        log.info("GetAllDrinksPaginated, status=end, response=[]", drinksPage);
        return drinksPage;
    }
}
