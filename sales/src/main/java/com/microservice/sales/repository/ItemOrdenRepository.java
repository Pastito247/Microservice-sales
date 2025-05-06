package com.microservice.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.ItemOrden;

public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long>{
    List<ItemOrden> findByOrden_Id(Long ordenId);

}
