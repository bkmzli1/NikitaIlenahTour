package ru.tour.model;

import javax.validation.constraints.NotEmpty;

public class AddressDto {
    @NotEmpty(message = "Укажите адрес")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
