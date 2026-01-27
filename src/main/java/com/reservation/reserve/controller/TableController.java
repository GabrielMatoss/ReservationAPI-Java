package com.reservation.reserve.controller;

import com.reservation.reserve.dto.TableDto;
import com.reservation.reserve.dto.TableRequest;
import com.reservation.reserve.service.TableService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<List<TableDto>> getAllTables() {
        List<TableDto> tables = tableService.getAllTables();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TableDto>> getAvailableTables(
            @RequestParam(required = false) Integer minCapacity) {
        List<TableDto> tables = tableService.getAvailableTables(minCapacity);
        return ResponseEntity.ok(tables);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TableDto> createTable(@Valid @RequestBody TableRequest request) {
        TableDto table = tableService.createTable(request);
        return new ResponseEntity<>(table, HttpStatus.CREATED);
    }
}
