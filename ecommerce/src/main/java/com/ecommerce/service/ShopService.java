package com.ecommerce.service;


import com.ecommerce.entity.Shop;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ShopRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả shops
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    // Lấy shop theo ID
    public Shop getShopById(Integer id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id " + id));
    }

    public Shop createShop(Shop shop) {
        User user = shop.getUser();

        // Kiểm tra nếu User đã tồn tại hoặc chưa có thì tạo mới
        if (user.getUserId() == null) {
            user = userRepository.save(user);  // Tạo mới User nếu chưa có ID
        } else {
            Integer userId = user.getUserId();
            user = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        }

        shop.setUser(user);  // Gán user đã tạo/cập nhật cho Shop
        return shopRepository.save(shop);
    }


    public Shop updateShop(Integer id, Shop shopDetails) {
        // Tìm shop hiện tại trong database
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id " + id));

        // Cập nhật thông tin của Shop
        shop.setShopName(shopDetails.getShopName());
        shop.setAddress(shopDetails.getAddress());
        shop.setPhone(shopDetails.getPhone());

        // Kiểm tra thông tin của User và cập nhật nếu cần
        User userDetails = shopDetails.getUser();
        if (userDetails != null) {
            if (userDetails.getUserId() == null) {
                // Tạo mới User nếu chưa có ID
                User newUser = userRepository.save(userDetails);
                shop.setUser(newUser);
            } else {
                // Cập nhật User nếu đã tồn tại
                User existingUser = userRepository.findById(userDetails.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userDetails.getUserId()));

                existingUser.setUserName(userDetails.getUserName());
                existingUser.setEmail(userDetails.getEmail());
                existingUser.setPassword(userDetails.getPassword());  // Mã hóa mật khẩu nếu cần

                // Lưu User đã cập nhật
                userRepository.save(existingUser);
                shop.setUser(existingUser);
            }
        }

        // Lưu Shop đã cập nhật
        return shopRepository.save(shop);
    }


    public void deleteShop(Integer id) {
        // Tìm shop theo ID
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id " + id));

        // Lấy thông tin User liên quan đến Shop
        User user = shop.getUser();

        // Xóa shop
        shopRepository.delete(shop);

        // Xóa luôn User nếu User tồn tại
            userRepository.delete(user);

    }

}
