package com.ecommerce.controller;


import com.ecommerce.entity.Manager;
import com.ecommerce.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    // Lấy tất cả managers
    @GetMapping
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    // Lấy manager theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Integer id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    // Tạo mới manager
    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
        return ResponseEntity.ok(managerService.createManager(manager));
    }

    // Cập nhật manager theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Integer id, @RequestBody Manager managerDetails) {
        return ResponseEntity.ok(managerService.updateManager(id, managerDetails));
    }

    // Xóa manager theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
