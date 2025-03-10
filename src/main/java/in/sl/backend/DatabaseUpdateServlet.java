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

@WebServlet("/DatabaseUpdateServlet")
public class DatabaseUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // ✅ 1️⃣ Get session details
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        String selectedClass = request.getParameter("class_name"); // Get from form

        if (userEmail == null || selectedClass == null) {
            System.out.println("⚠️ User email or class is null!");
            response.sendRedirect("payment-failed.jsp?msg=Session Expired");
            return;
        }

        try {
            // ✅ 2️⃣ Database Connection
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE users SET has_paid = ?, paid_class = ?, payment_status = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);  // Payment done
            stmt.setString(2, selectedClass);
            stmt.setString(3, "paid");
            stmt.setString(4, userEmail);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Database Updated Successfully!");
                response.sendRedirect("dashboard.jsp?msg=Payment Successful");
            } else {
                System.out.println("⚠️ No rows updated. Check email value!");
                response.sendRedirect("payment-failed.jsp?msg=User Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("payment-failed.jsp?msg=Database Error");
        }
    }
}

