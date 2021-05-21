package com.challenge.stock.dto;

import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BookDTO {

    private String drinkName;

    @NotNull(message = "Type is required")
    private Category type;

    @NotNull(message = "Quantity is required")
    private Long quantity;

    @NotNull(message = "Section is required")
    private Section section;

    private Date createdAt;

    @NotNull(message = "CreatedBy is required")
    private String createdBy;

}
