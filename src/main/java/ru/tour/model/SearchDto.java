package ru.tour.model;

import java.time.LocalDate;

public class SearchDto {
  public String country;
  public String city;
  public LocalDate dataStrat;
  public LocalDate dataStop;
  private int adults;
  private int children;
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDataStrat() {
        return dataStrat;
    }

    public void setDataStrat(LocalDate dataStrat) {
        this.dataStrat = dataStrat;
    }

    public LocalDate getDataStop() {
        return dataStop;
    }

    public void setDataStop(LocalDate dataStop) {
        this.dataStop = dataStop;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }
}
