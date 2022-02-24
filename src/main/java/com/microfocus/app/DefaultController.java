package com.microfocus.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
