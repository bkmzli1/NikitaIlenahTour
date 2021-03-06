package ru.tour.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tour.domain.User;
import ru.tour.model.UserEditBindingModel;
import ru.tour.model.UserRegisterBindingModel;
import ru.tour.repo.UserRepo;
import ru.tour.services.impl.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin

public class UserController {
    private final UserService userService;


    private final UserRepo userRepository;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepo userRepository,
                          ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @PostMapping(value = "/reg")
    @ResponseBody
    public Map<Object, Object> registerConfirm(@RequestBody @Valid UserRegisterBindingModel userRegisterBindingModel,
                                               BindingResult bindingResult) {

        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            UserRegisterBindingModel userServiceModel = this.modelMapper
                    .map(userRegisterBindingModel, UserRegisterBindingModel.class);
            this.userService.create(userServiceModel);
            strings.put("error", null);
        }
        return strings;
    }



    @RequestMapping("/user")
    public User user(Authentication authentication) {
        try {
            User user = userRepository.findUserById(((User) authentication.getPrincipal()).getId());
            return user;
        } catch (NullPointerException npe) {

        }
        return null;

    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public Object userID(@PathVariable String id) throws InterruptedException {
        try {
            User user = userRepository.findOneById(id);
            return user;
        }catch (NullPointerException e){
            Thread.sleep(1000);
            return null;
        }

    }

    @PostMapping("/save")
    @ResponseBody
    public User userSave(@RequestBody() UserRegisterBindingModel userRegisterBindingModel,
                         Authentication authentication) {
        User user = userRepository.findUserById(((User) authentication.getPrincipal()).getId());
        System.out
                .println(bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword()));
        ;
        if (bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword())) {
            user.setFirstName(userRegisterBindingModel.getFirstName());
            user.setMiddleName(userRegisterBindingModel.getMiddleName());
            user.setLastName(userRegisterBindingModel.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userRegisterBindingModel.getPassword()));
            userRepository.save(user);
        }
        return user;
    }

    @GetMapping("/executor")
    public Set<User> userEx() {
        Set<User> users = new TreeSet<User>(Comparator.comparing(User::getUsername));
        List<User> userByRole = userRepository.findUsersByAuthoritiesAuthority("EXECUTOR");
        users.addAll(userByRole);
        return users;
    }

    @GetMapping("/usersss")
    public Object users(Authentication authentication) {
        return userRepository.findUserById(((User) authentication.getPrincipal()).getId());
    }
   
    @GetMapping("/allusers")
    public Object allUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/edituserapi")
    public Object allUsers(@RequestBody @Valid UserEditBindingModel userEditBindingModel,
                           BindingResult bindingResult) {

        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            UserEditBindingModel userServiceModel = this.modelMapper
                    .map(userEditBindingModel, UserEditBindingModel.class);
            this.userService.edit(userServiceModel);
            strings.put("error", null);
        }
        return strings;
    }
}