package com.microfocus.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController
{
    private static final Logger log = LogManager.getLogger(ProductController.class);

    @GetMapping({"/", "/products"})
    public String showProductsPage()
    {
        log.info("ProductController:showProductsPage");
        return "products";
    }
}
