package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.BookingDao;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class BookingDaoImpl implements BookingDao {
    Connection con;

    public BookingDaoImpl(Connection con) { this.con = con; }

    @Override
    @Async
    public CompletableFuture<ArrayList<Booking>> findAll() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setIdBooking(rs.getLong(1));
                booking.setIdUser(rs.getLong(2));
                booking.setIdService(rs.getLong(3));
                booking.setDate(rs.getDate(4));
                booking.setTime(rs.getTime(5));
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(bookingList);
    }

    @Override
    @Async
    public CompletableFuture<Booking> findByPrimaryKey(Long id) {
        Booking booking=new Booking();
        String query = "SELECT * FROM bookings WHERE id_booking = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    booking.setIdBooking(rs.getLong(1));
                    booking.setIdUser(rs.getLong(2));
                    booking.setIdService(rs.getLong(3));
                    booking.setDate(rs.getDate(4));
                    booking.setTime(rs.getTime(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(booking);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> saveOrUpdate(Booking booking) {
        String query = "INSERT INTO bookings (id_user, id_service, data, ora) VALUES ( ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, booking.getIdUser());
            st.setLong(2, booking.getIdService());
            st.setDate(3, booking.getDate());
            st.setTime(4, booking.getTime());
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> delete(Long id) {
        String query = "DELETE FROM bookings WHERE id_booking = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException ignored) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    @Async
    public CompletableFuture<Boolean> isValid(Long bookingId) {
        String query = "COUNT(*) FROM bookings WHERE id_booking = ? && is_expired=false";
        boolean res=false;
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, bookingId);
            try (ResultSet rs = st.executeQuery()) {
                res=rs.next();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(res);
    }
}
