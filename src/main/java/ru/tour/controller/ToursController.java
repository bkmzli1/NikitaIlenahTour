package ru.tour.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tour.domain.*;
import ru.tour.model.*;
import ru.tour.repo.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/tours")
public class ToursController {

    private final ServicesRepo servicesRepo;
    private final ToursRepo toursRepo;
    private final CountryRepo countryRepo;
    private final CityRepo cityRepo;
    private final AddressRepo addressRepo;
    private final ImgRepo imgRepo;
    private final SvaziRepo svaziRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public ToursController(ServicesRepo servicesRepo, ToursRepo toursRepo, CountryRepo countryRepo, CityRepo cityRepo, AddressRepo addressRepo, ImgRepo imgRepo, SvaziRepo svaziRepo, ModelMapper modelMapper) {
        this.servicesRepo = servicesRepo;
        this.toursRepo = toursRepo;
        this.countryRepo = countryRepo;
        this.cityRepo = cityRepo;
        this.addressRepo = addressRepo;
        this.imgRepo = imgRepo;
        this.svaziRepo = svaziRepo;
        this.modelMapper = modelMapper;
    }

    @PutMapping(value = "/country")
    @ResponseBody
    public Object addCountries(@RequestBody @Valid CountryDto countryDto,
                               BindingResult bindingResult) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            Country country = new Country();
            country.setName(countryDto.getName());
            countryRepo.save(country);
            return countryRepo.findAll();
        }

    }

    @JsonView(Views.City.class)
    @GetMapping(value = "/country")
    @ResponseBody
    public Object getCountries() {
        return countryRepo.findAll();
    }

    @GetMapping(value = "/country/{name}/city/")
    @ResponseBody
    public Object getCity(@PathVariable String name) {
        return countryRepo.findByName(name);
    }

    @PutMapping(value = "/country/{name}/city/")
    @ResponseBody
    public Object addCity(@RequestBody @Valid CityDto cityDto,
                          BindingResult bindingResult, @PathVariable String name) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            Country country = countryRepo.findByName(name);
            try {
                Set<City> cities = country.getCities();
                City city = cityRepo.findByName(cityDto.getName());
                if (city == null) {
                    city = new City();
                }
                city.setName(cityDto.getName());
                cities.add(city);

                countryRepo.save(country);
                country.setCities(cities);
                return countryRepo.findAll();
            } catch (Exception e) {
                Set<City> cities = new HashSet<>();
                City city = cityRepo.findByName(cityDto.getName());
                if (city == null) {
                    city = new City();
                }
                city.setName(cityDto.getName());
                cities.add(city);
                cityRepo.save(city);
                countryRepo.save(country);
                country.setCities(cities);
                return countryRepo.findAll();
            }
        }
    }

    @PutMapping(value = "/city/{cityS}")
    @ResponseBody
    public Object addAddress(@RequestBody @Valid AddressDto addressDto,
                             BindingResult bindingResult, @PathVariable String cityS) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            City city = cityRepo.findByName(cityS);
            Address address = new Address();
            address.setName(addressDto.getName());
            try {
                Set<Address> addresses = city.getAddresses();
                addresses.add(address);
                city.setAddresses(addresses);
            } catch (Exception e) {
                city.setAddresses(new HashSet<>());
                Set<Address> addresses = city.getAddresses();
                addresses.add(address);
                city.setAddresses(addresses);
            }
            cityRepo.save(city);

        }
        return countryRepo.findAll();
    }

    @PutMapping(value = "/tour")
    @ResponseBody
    public Object addTour(@RequestBody @Valid TourDto tourDto,
                          BindingResult bindingResult) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            Country country = countryRepo.findByName(tourDto.getCountry());
            Set<City> cities = country.getCities();
            Tours userEntity = this.modelMapper.map(tourDto, Tours.class);
            userEntity.setIdGen(UUID.randomUUID().toString());
            userEntity.setImg(new HashSet<>());
            userEntity.setCreates(LocalDateTime.now());
            for (String imgId : tourDto.getImg()) {
                userEntity.getImg().add(imgRepo.getOne(imgId));
            }

            Set<Img> imgs = new HashSet<>();
            try {
                tourDto.getSizeServices().forEach(services -> {
                    services.getImg().forEach(img -> {
                        if (img.getB() == 1) {
                            Img imgDB = this.modelMapper.map(img, Img.class);
                            imgs.add(imgDB);
                        }
                    });
                });
            } catch (NullPointerException nullPointerException) {
            }

            userEntity.setServices(imgs);

            cities.stream().filter(city ->
                    city.getName().equals(tourDto.getCity())).forEach(city ->
                    city.getAddresses().stream().filter(addres ->
                            addres.getName().equals(tourDto.getAddress())).forEach(addres -> {
                        try {
                            Set<Tours> tours = addres.getTours();
                            tours.add(userEntity);
                            countryRepo.save(country);
                        } catch (Exception e) {
                            addres.setTours(new HashSet<>());
                            Set<Tours> tours = addres.getTours();
                            tours.add(userEntity);
                            countryRepo.save(country);
                        }

                    })
            );

            return toursRepo.findByIdGen(userEntity.getIdGen());
        }
    }

    @GetMapping(value = "/top/{size}")
    @ResponseBody
    public Object top(@PathVariable int size) {
        List<Tours> tours = this.toursRepo.findAll();
        Set<Tours> toursSort = new TreeSet<Tours>(Comparator.comparing(Tours::getCreates).reversed());
        toursSort.addAll(tours);
        tours.clear();
        tours.addAll(toursSort);
        List<Tours> toursTop = Lists.partition(tours, size).get(0);
        return toursTop;
    }

    @GetMapping(value = "/address/{tours}")
    @ResponseBody
    public Object addressTour(@PathVariable String tours) {
        Address actionByTours = addressRepo.findActionByTours(toursRepo.findById(tours).get());
        return actionByTours;
    }

    @GetMapping(value = "/city/{tours}")
    @ResponseBody
    public Object cityTour(@PathVariable String tours) {
        return cityRepo.findActionByAddressesTours(toursRepo.findById(tours).get());
    }

    @GetMapping(value = "/country/{tours}")
    @ResponseBody
    public Object countryTour(@PathVariable String tours) {
        return countryRepo.findActionByCitiesAddressesTours(toursRepo.findById(tours).get());
    }

    @PutMapping(value = "/countrys/tours")
    @ResponseBody
    public Object countryTours(@RequestBody Set<Tours> tours) {
        Set<Country> toursGet = new TreeSet<>(Comparator.comparing(Country::getId));

        tours.stream().filter(Objects::nonNull).forEach(tour -> {
            Tours tours1 = toursRepo.findById(tour.getId()).get();
            Country actionByCitiesAddressesTours = countryRepo.findActionByCitiesAddressesToursId(tour.getId());
            try {
                toursGet.add(actionByCitiesAddressesTours);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        toursGet.forEach(country -> country.getCities().forEach(city -> city.getAddresses().forEach(address -> {
            Set<Tours> toursToSort = address.getTours();
            Set<Tours> toursSort = new TreeSet<>(Comparator.comparing(Tours::getCreates).reversed());
            toursToSort.stream().filter(tours1 -> isColextion(tours1, tours)).forEach(toursSort::add);
            address.setTours(toursSort);
        })));
        return toursGet;
    }

    private boolean isColextion(Tours tours1, Set<Tours> tours) {
        for (Tours t : tours) {
            if (t.getId().equals(tours1.getId())) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/services")
    @ResponseBody
    public Object servicesTours() {
        return servicesRepo.findAll();
    }

    @GetMapping(value = "/maxData")
    @ResponseBody
    public Object MaxDataTours() {
        LocalDate dataStop = toursRepo.getMaxCreates().getDataStop();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(dataStop);
    }

    @GetMapping(value = "/minData")
    @ResponseBody
    public Object MinDataTours() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataStart = toursRepo.getMinCreates().getDataStart();
        String format = formatter.format(dataStart);
        LocalDate date = LocalDate.now();
        if (dataStart.compareTo(date) == 0 || dataStart.compareTo(date) < 0) {
            format = formatter.format(date);
            return format;
        }
        return format;
    }

    @PostMapping(value = "/search")
    @ResponseBody
    public Object search(@RequestBody SearchDto searchDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(formatter.format(searchDto.getDataStrat()));
        Set<Country> countrys = this.countryRepo.findByNameAndCitiesName(searchDto.getCountry(), searchDto.getCity());
        countrys.forEach(country -> country.getCities().stream().filter(Objects::nonNull)
                .forEach(city -> city.getAddresses().stream().filter(Objects::nonNull)
                        .forEach(address -> {
                            address.getTours().removeIf(tours -> tours.getDataStart().compareTo(searchDto.dataStrat) < 0);
                            try {
                                address.getTours().removeIf(tours -> tours.getDataStop().plusDays(tours.getHowManyNights()).compareTo(searchDto.getDataStop()) >= 0);
                            } catch (Exception e) {
                            }
                            try {
                                address.getTours().removeIf(tours -> tours.getAdultsMax() <= searchDto.getAdults() & searchDto.getAdults() >= tours.getAdultsMin());
                            } catch (Exception e) {

                            }
                            try {
                                address.getTours().removeIf(tours -> tours.getChildrenMax() <= searchDto.getChildren() & searchDto.getChildren() >= tours.getChildrenMin());
                            } catch (Exception e) {

                            }

                            Set<Tours> toursSort = new TreeSet<Tours>(Comparator.comparing(Tours::getCreates).reversed());
                            toursSort.addAll(address.getTours());
                            address.setTours(toursSort);
                        })));
        return countrys;
    }

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public Object getTour(@PathVariable String id) {
        return toursRepo.findById(id);
    }

    @GetMapping(value = "/svaze")
    @ResponseBody
    @JsonView(Views.Tyr.class)
    public Object getSvaze() {
        List<Svazi> all = svaziRepo.findAll();
        return all;
    }

    @GetMapping(value = "/svaze/{id}")
    @ResponseBody
    @JsonView(Views.Tyr.class)
    public Object getSvaze(@PathVariable String id) {
        return toursRepo.findBySvazisId(id);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public Object getall() {
        List<Country> all = countryRepo.findAll();
        return all;
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public Object getdelite(@PathVariable String id) {
        Address actionByToursId = addressRepo.findActionByToursId(id);
        actionByToursId.getTours().stream().filter(tours -> tours.getId().equals(id)).forEach(tours -> actionByToursId.getTours().remove(tours));
        addressRepo.save(actionByToursId);
        toursRepo.deleteById(id);
        return null;
    }

    @DeleteMapping(value = "/delete/country/{id}")
    @ResponseBody
    public Object getdelitecpu(@PathVariable String id) {
        Country byId = countryRepo.findById(id).get();
        byId.getCities().forEach(city -> {
            city.getAddresses().forEach(address -> {
                address.getTours().clear();
            });
            city.getAddresses().clear();
        });
        byId.getCities().clear();
        countryRepo.save(byId);
        countryRepo.deleteById(id);
        return null;
    }

    @DeleteMapping(value = "/delete/addres/{id}")
    @ResponseBody
    public Object getdeliteadd(@PathVariable String id) {
        City byAddressesId = cityRepo.findByAddressesId(id);
        byAddressesId.getAddresses().stream().filter(address -> address.getId().equals(id)).forEach(address -> byAddressesId.getAddresses().remove(address));
        cityRepo.save(byAddressesId);
        addressRepo.deleteById(id);
        return null;
    }

    @DeleteMapping(value = "/delete/city/{id}")
    @ResponseBody
    public Object getdelitecity(@PathVariable String id) {
        Country byCitiesId = countryRepo.findByCitiesId(id);
        byCitiesId.getCities().stream().filter(city -> city.getId().equals(id)).forEach(city -> byCitiesId.getCities().remove(city));
        countryRepo.save(byCitiesId);
        cityRepo.deleteById(id);
        return null;
    }

    @PutMapping(value = "/get/{id}")
    @ResponseBody
    public Object putTour(@PathVariable String id, @RequestBody SvaziDto svaziDto) {
        Svazi userEntity = this.modelMapper.map(svaziDto, Svazi.class);
        Tours tours = toursRepo.findById(id).get();
        svaziRepo.save(userEntity);
        try {
            tours.getSvazis().add(userEntity);
        } catch (Exception e) {
            tours.setSvazis(new HashSet<>());
            tours.getSvazis().add(userEntity);
        }
        toursRepo.save(tours);
        return toursRepo.findById(id);
    }

    @DeleteMapping(value = "/get/{id}/{idSvazi}")
    @ResponseBody
    public Object putTourdel(@PathVariable String id, @PathVariable String idSvazi) {
        List<Tours> all = toursRepo.findAll();
        all.forEach(tours ->  tours.getSvazis().removeIf(svazi -> svazi.getId().equals(idSvazi)));
        toursRepo.saveAll(all);
        svaziRepo.deleteById(idSvazi);
        return null;
    }
}
