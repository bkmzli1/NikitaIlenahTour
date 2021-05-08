package ru.tour.model;

import java.time.LocalDate;
import java.util.Set;

public class TourDto {
    private String address;
    private String city;
    private String country;
    private String name;
    private LocalDate dataStart;
    private LocalDate dataStop;
    private Set<Services> sizeServices;
    private Set<String> img;
    private String text;
    private int adultsPrice;
    private int childrenPrice;
    private int humanMax;
    private int adultsMax;
    private int adultsMin;
    private int childrenMax;
    private int childrenMin;
    private int howManyNights;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getHumanMax() {
        return humanMax;
    }

    public void setHumanMax(int humanMax) {
        this.humanMax = humanMax;
    }

    public int getAdultsMax() {
        return adultsMax;
    }

    public void setAdultsMax(int adultsMax) {
        this.adultsMax = adultsMax;
    }

    public int getAdultsMin() {
        return adultsMin;
    }

    public void setAdultsMin(int adultsMin) {
        this.adultsMin = adultsMin;
    }

    public int getChildrenMax() {
        return childrenMax;
    }

    public void setChildrenMax(int childrenMax) {
        this.childrenMax = childrenMax;
    }

    public int getChildrenMin() {
        return childrenMin;
    }

    public void setChildrenMin(int childrenMin) {
        this.childrenMin = childrenMin;
    }

    public int getHowManyNights() {
        return howManyNights;
    }

    public void setHowManyNights(int howManyNights) {
        this.howManyNights = howManyNights;
    }

    public Set<Services> getSizeServices() {
        return sizeServices;
    }

    public void setSizeServices(Set<Services> sizeServices) {
        this.sizeServices = sizeServices;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getImg() {
        return img;
    }

    public void setImg(Set<String> img) {
        this.img = img;
    }

    public static class Services {
        private String id;
        private String name;
        private Set<Img> img;

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

        public Set<Img> getImg() {
            return img;
        }

        public void setImg(Set<Img> img) {
            this.img = img;
        }

        public static class Img {
            String id;
            String img;
            int b;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getB() {
                return b;
            }

            public void setB(int b) {
                this.b = b;
            }
        }
    }

}
