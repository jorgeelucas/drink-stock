package com.challenge.stock.domain;

import com.challenge.stock.domain.enums.Category;
import com.challenge.stock.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long quantity;
    private Category type;
    @ManyToOne
    private Section section;
    private Date createdAt;
    private String createdBy;

    public Drink(BookDTO dto) {
        this.name = dto.getDrinkName();
        this.quantity = dto.getQuantity();
        this.type = dto.getType();
        this.section = dto.getSection();
        this.createdAt = new Date();
        this.createdBy = dto.getCreatedBy();
    }
}
