package com.reservation.reserve.service;

import com.reservation.reserve.domain.entity.RestaurantTable;
import com.reservation.reserve.dto.TableDto;
import com.reservation.reserve.dto.TableRequest;
import com.reservation.reserve.exception.ResourceNotFoundException;
import com.reservation.reserve.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Transactional
    public TableDto createTable(TableRequest request) {
        // Check if table number already exists
        if (tableRepository.findByTableNumber(request.getTableNumber()).isPresent()) {
            throw new IllegalArgumentException("Table number already exists");
        }

        RestaurantTable table = new RestaurantTable();
        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setLocation(request.getLocation());

        RestaurantTable savedTable = tableRepository.save(table);
        return convertToDto(savedTable);
    }

    public List<TableDto> getAllTables() {
        return tableRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TableDto> getAvailableTables(Integer minCapacity) {
        if (minCapacity != null) {
            return tableRepository.findByCapacityGreaterThanEqual(minCapacity).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        return getAllTables();
    }

    public RestaurantTable getTableById(@org.springframework.lang.NonNull Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
    }

    private TableDto convertToDto(RestaurantTable table) {
        return new TableDto(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getLocation());
    }
}
