package com.reservation.reserve.repository;

import com.reservation.reserve.domain.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(String tableNumber);

    List<RestaurantTable> findByCapacityGreaterThanEqual(Integer capacity);
}
