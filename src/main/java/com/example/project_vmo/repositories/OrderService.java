package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderService extends JpaRepository<Order,Integer> {

}
