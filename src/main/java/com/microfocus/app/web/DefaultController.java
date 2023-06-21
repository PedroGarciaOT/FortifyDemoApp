package com.microfocus.app.web;

import com.microfocus.app.entity.Product;
import com.microfocus.app.entity.SubscribeUserRequest;
import com.microfocus.app.repository.ProductRepository;
import com.microfocus.app.service.FileSystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class DefaultController {
    private static final Logger log = LogManager.getLogger(DefaultController.class);

    private final ProductRepository productRepository;

    private final FileSystemService fileSystemService;

    public DefaultController(ProductRepository productRepository, FileSystemService fileSystemService) {
        this.productRepository = productRepository;
        this.fileSystemService = fileSystemService;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model, Principal principal) {
        return "login";
    }

    @GetMapping("/user")
    public String userHome(HttpServletRequest request, Model model, Principal principal) {
        return "user/index";
    }

    @GetMapping("/admin")
    public String adminHome(HttpServletRequest request, Model model, Principal principal) {
        return "admin/index";
    }


    @GetMapping({"/", "/products"})
    public String showProductsPage(Model model, @RequestParam(value = "keywords", required = false) String keywords) {
        log.debug("DefaultController:showProductsPage");
        log.debug("Searching for products using keywords=" + keywords);

        List<Product> products = new ArrayList<>();
        try {
            if (keywords == null || keywords.equals("")) {
                products = productRepository.findAll();
                model.addAttribute("haveKeywords", false);
            } else {
                products = productRepository.findByName(keywords);
                model.addAttribute("haveKeywords", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("products", products);
        model.addAttribute("productsCount", products.size());
        model.addAttribute("productsTotal", productRepository.count());
        return "index";
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable("id") UUID productId, Model model) {
        log.debug("DefaultController:viewProduct");

        List<Product> products = productRepository.findById(productId);
        if (products.size() > 0) {
            model.addAttribute("p", products.get(0));
            model.addAttribute("dateNow", new Date());
        } else {
            model.addAttribute("message", "Internal error accessing product!");
            return "products/not-found";
        }
        return "products/index";
    }

    @GetMapping("/products/{productId}/downloadfile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "productId") String productId,
                                                 @PathVariable String fileName, HttpServletRequest request) {
        log.debug("DefaultController:downloadFile");

        Resource resource;

        File dataDir = null;
        try {
            dataDir = ResourceUtils.getFile("classpath:data");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        log.debug("Using data directory: " + dataDir.getAbsolutePath());
        String fileBasePath = dataDir.getAbsolutePath() + File.separatorChar + productId + File.separatorChar;
        Path path = Paths.get(fileBasePath + fileName);
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.debug("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = {"/api/subscribe-user"}, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<String> subscribeUser(@RequestBody SubscribeUserRequest newUser) {
        log.debug("DefaultController:subscribeUser");
        try {
            fileSystemService.writeUser(newUser.getId(), newUser.getFirstName() + " " + newUser.getLastName(), newUser.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
