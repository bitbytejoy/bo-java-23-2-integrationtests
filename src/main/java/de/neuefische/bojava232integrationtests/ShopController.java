package de.neuefische.bojava232integrationtests;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/products")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    public List<Product> get() {
        return shopService.getAll();
    }

    @PostMapping
    public Product post(@RequestBody Product product) {
        return shopService.save(product);
    }
}
