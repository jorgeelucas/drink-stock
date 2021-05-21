package com.challenge.stock.domain.enums;

public enum Category {

    ALCOHOLIC(500L),
    NON_ALCOHOLIC(400L);

    private Long capacity;

    Category(Long capacity) {
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return this.capacity;
    }

    public static boolean isMember(String typeName) {
        Category[] types = Category.values();
        for (Category type : types)
            if (type.toString().equalsIgnoreCase(typeName))
                return true;
        return false;
    }

}
