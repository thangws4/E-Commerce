package com.ecommerce.service;


import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User userDetails) {
        // Tìm user theo ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Cập nhật thông tin của User
        user.setUserName(userDetails.getUserName());
        user.setPassword(userDetails.getPassword());  // Bạn có thể mã hóa mật khẩu ở đây nếu cần
        user.setEmail(userDetails.getEmail());
        user.setAvatar(userDetails.getAvatar());

        // Cập nhật Role của User
        if (userDetails.getRole() != null) {
            Integer roleId = userDetails.getRole().getRoleId();
            // Kiểm tra xem Role có tồn tại không
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
            user.setRole(role);  // Gán Role cho User
        }

        // Lưu User đã cập nhật
        return userRepository.save(user);
    }


    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
