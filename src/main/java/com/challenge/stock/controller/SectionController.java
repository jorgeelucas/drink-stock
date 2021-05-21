package com.challenge.stock.controller;

import com.challenge.stock.domain.Section;
import com.challenge.stock.dto.TotalsSectionDTO;
import com.challenge.stock.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        return ResponseEntity.ok(sectionService.getAllSections());
    }

    @GetMapping("/amount/{type}/type")
    public ResponseEntity<TotalsSectionDTO> findTotalForType(@PathVariable("type") String type) {
        TotalsSectionDTO responseDto = this.sectionService.findTotalForType(type);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/amount/{sectionId}/section")
    public ResponseEntity<Long> findTotalForSection(@PathVariable("sectionId") String id) {
        Long response = this.sectionService.findTotalForSection(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available/{amount}/{type}")
    public ResponseEntity<List<Section>> findAvailableForAmountAndType(@PathVariable("amount") Long amount, @PathVariable("type") String type) {
        List<Section> sectionList = this.sectionService.availableSectionsForAmount(amount, type);
        return ResponseEntity.ok(sectionList);
    }

}
