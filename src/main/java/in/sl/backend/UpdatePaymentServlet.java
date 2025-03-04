package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdatePaymentServlet")
public class UpdatePaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        String classLevel = request.getParameter("class_level");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET has_paid=TRUE, paid_class=? WHERE id=?");
            ps.setString(1, classLevel);
            ps.setInt(2, userId);
            ps.executeUpdate();
            
            response.sendRedirect("dashboard.jsp?msg=Payment Successful");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("payment.jsp?msg=Error updating payment status");
        }
    }
}


