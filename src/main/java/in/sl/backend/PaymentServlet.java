package in.sl.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String className = request.getParameter("class_name");
        double amount = 0;
        Connection conn = null; 

        try {
            // Database Connection
            conn = DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed!");
            }

            PreparedStatement ps = conn.prepareStatement("SELECT amount FROM class_fees WHERE class_name=?");
            ps.setString(1, className);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                amount = rs.getDouble("amount");
            } else {
                throw new Exception("Class fee not found for " + className);
            }

            System.out.println("Class: " + className + ", Amount: " + amount); // Debug Log

            // Razorpay Client
            RazorpayClient client = new RazorpayClient("rzp_test_cmsgJBXhaTTYHW", "GfadWZEoSXR2PSK5IgJPUqRL");

            // Create Order
            JSONObject options = new JSONObject();
            options.put("amount", (int)(amount * 100)); // Convert to paise
            options.put("currency", "INR");
            options.put("receipt", "order_" + System.currentTimeMillis());
            options.put("payment_capture", 1);

            Order order = client.orders.create(options);
            System.out.println("Order Created: " + order); // Debug Log

            // Store order details in session
            HttpSession session = request.getSession();
            session.setAttribute("razorpay_order_id", order.get("id"));
            session.setAttribute("class_name", className);
            session.setAttribute("amount", amount);

            // Send JSON Response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", order.get("id").toString());
            jsonResponse.put("amount", order.get("amount").toString());

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());

        } catch (RazorpayException e) {
            e.printStackTrace();
            System.err.println("Razorpay Exception: " + e.getMessage());
            response.sendRedirect("payment.jsp?msg=Payment Failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            response.sendRedirect("payment.jsp?msg=Error Occurred");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}











