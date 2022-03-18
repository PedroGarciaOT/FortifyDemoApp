package com.microfocus.app.web;

import com.microfocus.app.entity.Product;
import com.microfocus.app.entity.SubscribeUserRequest;
import com.microfocus.app.repository.ProductRepository;
import com.microfocus.app.service.FileSystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController
{
    private static final Logger log = LogManager.getLogger(DefaultController.class);

    private final ProductRepository productRepository;

    private final FileSystemService fileSystemService;

    public DefaultController(ProductRepository productRepository, FileSystemService fileSystemService) {
        this.productRepository = productRepository;
        this.fileSystemService = fileSystemService;
    }

    @GetMapping({"/", "/products"})
    //public ModelAndView showProductsPage(@RequestParam(value = "keywords", required = false)String keywords)
    public String showProductsPage(Model model, @RequestParam(value = "keywords", required = false)String keywords)
    {
        log.debug("DefaultController:showProductsPage");
        log.debug("Searching for products using keywords="+keywords);

        List<Product> products = new ArrayList<>();
        try {
            if (keywords == null || keywords.equals("")) {
                products = productRepository.findAll();
                model.addAttribute("haveKeywords", false);
            } else {
                products = productRepository.findByName(keywords);
                model.addAttribute("haveKeywords", true);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("products", products);
        model.addAttribute("productsCount", products.size());
        model.addAttribute("productsTotal", productRepository.count());
        return "index";
    }

    @PostMapping(value = {"/api/subscribe-user"}, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<String> subscribeUser(@RequestBody SubscribeUserRequest newUser) {
        log.debug("DefaultController:subscribeUser");
        try {
            fileSystemService.writeUser(newUser.getId(), newUser.getFirstName()+ " " + newUser.getLastName(), newUser.getEmail());
            log.debug("Subscribed a user to the newsletter: " + newUser.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
