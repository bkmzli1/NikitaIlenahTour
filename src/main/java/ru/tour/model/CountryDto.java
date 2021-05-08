package ru.tour.model;

import ru.tour.model.validations.IsCountryTaken;

import javax.validation.constraints.NotEmpty;

public class CountryDto {
    @IsCountryTaken
    @NotEmpty(message = "Укажите название страны")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
