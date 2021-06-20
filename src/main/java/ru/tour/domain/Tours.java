package ru.tour.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tours")
public class Tours {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String idGen;

    @JsonView(Views.Tyr.class)
    private String name;
    @Column(columnDefinition="TEXT")
    private String text;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private Country countryOfArrival;
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private Set<Country> permittedСountries;

    private int adultsPrice;
    private int childrenPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataStop;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creates;
    private int howManyNights;
    private int adultsMin;
    private int adultsMax;
    private int humanMax;
    private int childrenMin;
    private int childrenMax;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Img> img;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Img> services;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonView(Views.Tyr.class)
    private Set<Svazi> svazis;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdultsPrice() {
        return adultsPrice;
    }

    public void setAdultsPrice(int adultsPrice) {
        this.adultsPrice = adultsPrice;
    }

    public int getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(int childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public LocalDate getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataStop() {
        return dataStop;
    }

    public void setDataStop(LocalDate dataStop) {
        this.dataStop = dataStop;
    }

    public LocalDateTime getCreates() {
        return creates;
    }

    public void setCreates(LocalDateTime creates) {
        this.creates = creates;
    }

    public int getHowManyNights() {
        return howManyNights;
    }

    public void setHowManyNights(int howManyNights) {
        this.howManyNights = howManyNights;
    }

    public int getAdultsMin() {
        return adultsMin;
    }

    public void setAdultsMin(int adultsMin) {
        this.adultsMin = adultsMin;
    }

    public int getAdultsMax() {
        return adultsMax;
    }

    public void setAdultsMax(int adultsMax) {
        this.adultsMax = adultsMax;
    }

    public int getHumanMax() {
        return humanMax;
    }

    public void setHumanMax(int humanMax) {
        this.humanMax = humanMax;
    }

    public int getChildrenMin() {
        return childrenMin;
    }

    public void setChildrenMin(int childrenMin) {
        this.childrenMin = childrenMin;
    }

    public int getChildrenMax() {
        return childrenMax;
    }

    public void setChildrenMax(int childrenMax) {
        this.childrenMax = childrenMax;
    }

    public Set<Img> getServices() {
        return services;
    }

    public void setServices(Set<Img> services) {
        this.services = services;
    }

    public Set<Img> getImg() {
        return img;
    }

    public void setImg(Set<Img> img) {
        this.img = img;
    }

    public String getIdGen() {
        return idGen;
    }

    public void setIdGen(String idGen) {
        this.idGen = idGen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Svazi> getSvazis() {
        return svazis;
    }

    public void setSvazis(Set<Svazi> svazis) {
        this.svazis = svazis;
    }
}
