package com.reservation.reserve.service;

import com.reservation.reserve.domain.entity.Reservation;
import com.reservation.reserve.domain.entity.RestaurantTable;
import com.reservation.reserve.domain.entity.User;
import com.reservation.reserve.domain.enums.ReservationStatus;
import com.reservation.reserve.dto.ReservationRequest;
import com.reservation.reserve.dto.ReservationResponse;
import com.reservation.reserve.exception.ReservationConflictException;
import com.reservation.reserve.exception.ResourceNotFoundException;
import com.reservation.reserve.exception.UnauthorizedException;
import com.reservation.reserve.repository.ReservationRepository;
import com.reservation.reserve.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {

        private final ReservationRepository reservationRepository;
        private final UserRepository userRepository;
        private final TableService tableService;

        public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository,
                        TableService tableService) {
                this.reservationRepository = reservationRepository;
                this.userRepository = userRepository;
                this.tableService = tableService;
        }

        @Transactional
        public ReservationResponse createReservation(ReservationRequest request) {
                // Get current authenticated user
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // Get table
                RestaurantTable table = tableService.getTableById(Objects.requireNonNull(request.getTableId()));

                // Check for double-booking using a 2-hour window
                LocalDateTime startTime = request.getReservationDateTime().minusHours(1);
                LocalDateTime endTime = request.getReservationDateTime().plusHours(1);

                boolean hasConflict = reservationRepository.existsActiveReservationForTableInTimeRange(
                                table.getId(), startTime, endTime);

                if (hasConflict) {
                        throw new ReservationConflictException(
                                        "Table is already reserved for the requested time slot");
                }

                // Create reservation
                Reservation reservation = new Reservation();
                reservation.setUser(user);
                reservation.setTable(table);
                reservation.setReservationDateTime(request.getReservationDateTime());
                reservation.setStatus(ReservationStatus.ACTIVE);

                Reservation savedReservation = reservationRepository.save(reservation);
                return convertToDto(savedReservation);
        }

        public List<ReservationResponse> getUserReservations() {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                return reservationRepository.findByUserOrderByReservationDateTimeDesc(user).stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList());
        }

        @Transactional
        public void cancelReservation(Long reservationId) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                Reservation reservation = reservationRepository.findById(Objects.requireNonNull(reservationId))
                                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

                // Check if user owns the reservation or is admin
                if (!reservation.getUser().getId().equals(user.getId()) &&
                                !user.getRole().name().equals("ADMIN")) {
                        throw new UnauthorizedException("You are not authorized to cancel this reservation");
                }

                // Cancel the reservation
                reservation.setStatus(ReservationStatus.CANCELLED);
                reservationRepository.save(reservation);
        }

        private ReservationResponse convertToDto(Reservation reservation) {
                return new ReservationResponse(
                                reservation.getId(),
                                reservation.getUser().getId(),
                                reservation.getUser().getUsername(),
                                reservation.getTable().getId(),
                                reservation.getTable().getTableNumber(),
                                reservation.getReservationDateTime(),
                                reservation.getStatus().name(),
                                reservation.getCreatedAt());
        }
}
