package com.ecommerce.controller;


import com.ecommerce.entity.Role;
import com.ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Lấy tất cả các vai trò
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Lấy một vai trò theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // Tạo mới một vai trò
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    // Cập nhật một vai trò theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer id, @RequestBody Role roleDetails) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDetails));
    }

    // Xóa một vai trò theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
