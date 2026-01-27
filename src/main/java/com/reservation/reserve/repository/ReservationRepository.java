package com.reservation.reserve.repository;

import com.reservation.reserve.domain.entity.Reservation;
import com.reservation.reserve.domain.entity.User;
import com.reservation.reserve.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

        List<Reservation> findByUserOrderByReservationDateTimeDesc(User user);

        List<Reservation> findByUserAndStatus(User user, ReservationStatus status);

        @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
                        "FROM Reservation r " +
                        "WHERE r.table.id = :tableId " +
                        "AND r.status = 'ACTIVE' " +
                        "AND r.reservationDateTime BETWEEN :startTime AND :endTime")
        boolean existsActiveReservationForTableInTimeRange(
                        @Param("tableId") Long tableId,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
}
