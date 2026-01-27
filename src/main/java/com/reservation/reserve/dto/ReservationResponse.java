package com.reservation.reserve.dto;

import java.time.LocalDateTime;

public class ReservationResponse {

    private Long id;
    private Long userId;
    private String username;
    private Long tableId;
    private String tableNumber;
    private LocalDateTime reservationDateTime;
    private String status;
    private LocalDateTime createdAt;

    public ReservationResponse() {
    }

    public ReservationResponse(Long id, Long userId, String username, Long tableId, String tableNumber,
            LocalDateTime reservationDateTime, String status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.reservationDateTime = reservationDateTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
