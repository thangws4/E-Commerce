package com.ecommerce.controller;


import com.ecommerce.entity.Shop;
import com.ecommerce.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    // Lấy tất cả shops
    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    // Lấy shop theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Integer id) {
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    // Tạo mới shop
    @PostMapping
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.createShop(shop));
    }

    // Cập nhật shop theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable Integer id, @RequestBody Shop shopDetails) {
        return ResponseEntity.ok(shopService.updateShop(id, shopDetails));
    }

    // Xóa shop theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id) {
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }
}
