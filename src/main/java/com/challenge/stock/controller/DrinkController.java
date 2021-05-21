package com.challenge.stock.controller;

import com.challenge.stock.domain.Drink;
import com.challenge.stock.dto.BookDTO;
import com.challenge.stock.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/drinks")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping
    public ResponseEntity<Page<Drink>> getAllDrinks(@SortDefault.SortDefaults({
            @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC),
            @SortDefault(sort = "section.name", direction = Sort.Direction.ASC)
    }) Pageable pageable) {
        return ResponseEntity.ok(this.drinkService.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.drinkService.findById(id));
    }

    @PostMapping("/book")
    public ResponseEntity<Void> bookDrink(@RequestBody @Valid BookDTO dto) {
        this.drinkService.book(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
