package com.ecommerce.controller;

import com.ecommerce.entity.Voucher;
import com.ecommerce.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable Integer id) {
        Optional<Voucher> voucher = voucherService.getVoucherById(id);
        return voucher.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Voucher createVoucher(@RequestBody Voucher voucher) {
        return voucherService.createVoucher(voucher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable Integer id, @RequestBody Voucher voucherDetails) {
        Voucher updatedVoucher = voucherService.updateVoucher(id, voucherDetails);
        return ResponseEntity.ok(updatedVoucher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Integer id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
