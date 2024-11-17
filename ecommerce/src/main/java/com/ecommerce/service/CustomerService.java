package com.ecommerce.service;


import com.ecommerce.entity.Customer;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy một khách hàng theo ID
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    // Tạo mới một khách hàng (và User nếu chưa tồn tại)
    public Customer createCustomer(Customer customer) {
        User user = customer.getUser();

        // Nếu User chưa có trong database, thì tạo mới User
        if (user.getUserId() == null) {
            user = userRepository.save(user);
        } else {
            // Lưu userId vào biến tạm để tránh thay đổi biến user trong lambda
            Integer userId = user.getUserId();

            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        }


        // Sau khi tạo User, tạo Customer
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    // Cập nhật khách hàng và user theo ID
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        // Tìm customer hiện tại trong database
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        // Cập nhật thông tin của Customer
        customer.setCustomerName(customerDetails.getCustomerName());
        customer.setAddress(customerDetails.getAddress());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());

        // Cập nhật thông tin của User nếu có
        User user = customer.getUser();
        if (customerDetails.getUser() != null) {
            User userDetails = customerDetails.getUser();

            // Nếu User tồn tại thì cập nhật các thông tin cần thiết
            if (user != null) {
                user.setUserName(userDetails.getUserName());
                user.setEmail(userDetails.getEmail());
                user.setPassword(userDetails.getPassword());
                user.setAvatar(userDetails.getAvatar());

                // Lưu User sau khi cập nhật
                userRepository.save(user);
            }
        }

        // Lưu lại thông tin customer sau khi cập nhật
        return customerRepository.save(customer);
    }


    // Xóa khách hàng theo ID
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        // Lấy thông tin User liên quan đến Shop
        User user = customer.getUser();

        // Xóa shop
        customerRepository.delete(customer);

        // Xóa luôn User nếu User tồn tại
        userRepository.delete(user);

    }
}
