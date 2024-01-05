package it.unical.demacs.backend.Controller.Servlet;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

@CrossOrigin(origins = "http://localhost:4200")
@WebServlet("/api/booking/delete")
public class InsertBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idUser= Long.valueOf(request.getParameter("idUser"));
        Long idService=Long.valueOf(request.getParameter("idService"));
        Date date= Date.valueOf(request.getParameter("data"));
        Time time=Time.valueOf(request.getParameter("time"));
        try {
            if (DatabaseHandler.getInstance().getBookingDao().saveOrUpdate(new Booking(idUser, idService, date, time)).join()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Successful insert of the booking");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Insert failed");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}