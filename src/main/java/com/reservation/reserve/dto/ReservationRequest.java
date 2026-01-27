package com.reservation.reserve.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationRequest {

    @NotNull(message = "Table ID is required")
    private Long tableId;

    @NotNull(message = "Reservation date and time is required")
    @Future(message = "Reservation must be in the future")
    private LocalDateTime reservationDateTime;

    public ReservationRequest() {
    }

    public ReservationRequest(Long tableId, LocalDateTime reservationDateTime) {
        this.tableId = tableId;
        this.reservationDateTime = reservationDateTime;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }
}
