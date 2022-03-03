package com.microfocus.app.web;

import com.microfocus.app.entity.SubscribeUserRequest;
import com.microfocus.app.service.FsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class DefaultController
{
    private static final Logger log = LogManager.getLogger(DefaultController.class);

    @GetMapping({"/", "/products"})
    public String showProductsPage()
    {
        log.info("DefaultController:showProductsPage");
        return "products/index";
    }

    @PostMapping(value = {"/api/subscribe-user"}, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<String> subscribeUser(@RequestBody SubscribeUserRequest newUser) {
        log.debug("API::Subscribing a user to the newsletter: " + newUser.toString());
        try {
            FsService.writeUser(newUser.getId(), newUser.getFirstName()+ " " + newUser.getLastName(), newUser.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
