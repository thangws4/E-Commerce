package com.ecommerce.repository;

import com.ecommerce.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findByUser_UserId(Integer userId);

}
