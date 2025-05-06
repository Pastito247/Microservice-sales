package com.microservice.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.ItemCarrito;

public interface ItemCarritoReposiory extends JpaRepository<ItemCarrito, Long> {
   List<ItemCarrito> findByCarrito_Id(Long carritoId); 
}
