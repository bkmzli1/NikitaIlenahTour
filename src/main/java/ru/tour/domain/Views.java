package ru.tour.domain;


public final class Views {

    public interface Id {}
    public interface BasicCountry extends Id {}
    public interface Country extends BasicCountry {}
    public interface BasicCity extends  Country{}
    public interface City extends  BasicCity{}
    public interface Tyr extends Id {};


}

