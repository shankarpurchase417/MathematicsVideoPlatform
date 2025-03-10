package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PaymentSuccessServlet")
public class PaymentSuccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Payment Details
        String paymentId = request.getParameter("razorpay_payment_id");
        String orderId = request.getParameter("razorpay_order_id");
        String signature = request.getParameter("razorpay_signature");
        String selectedClass = request.getParameter("class_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        try {
            // Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathvideos", "root", "password");

            // Insert Payment Record
            String query = "INSERT INTO payments (payment_id, order_id, signature, class_name, username, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, paymentId);
            pst.setString(2, orderId);
            pst.setString(3, signature);
            pst.setString(4, selectedClass);
            pst.setString(5, username);
            pst.setString(6, email);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                response.sendRedirect("payment-success.jsp");
            } else {
                response.getWriter().write("❌ Payment Failed!");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






