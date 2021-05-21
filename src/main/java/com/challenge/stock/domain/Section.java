package com.challenge.stock.domain;

import com.challenge.stock.domain.enums.Category;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Section {

    @Id
    private String id;
    private String name;
    private Category type;
    private Long fillment;
    private Long capacity;

}
