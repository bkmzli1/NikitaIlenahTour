package ru.tour.model;

import javax.validation.constraints.NotEmpty;

public class CityDto {
    @NotEmpty(message = "Укажите название города")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
