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

import com.razorpay.*;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String className = request.getParameter("class_name");
        double amount = 0;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT amount FROM class_fees WHERE class_name=?");
            ps.setString(1, className);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                amount = rs.getDouble("amount");
            }

            // Razorpay Order Creation
            RazorpayClient client = new RazorpayClient("YOUR_KEY_ID", "YOUR_KEY_SECRET");
            
            JSONObject options = new JSONObject();
            options.put("amount", (int)(amount * 100)); // Razorpay takes amount in paise
            options.put("currency", "INR");
            options.put("receipt", "order_receipt_" + System.currentTimeMillis());
            Order order = client.orders.create(options);

            // Store Payment Details in Session
            HttpSession session = request.getSession();
            session.setAttribute("razorpay_order_id", order.get("id"));
            session.setAttribute("class_name", className);
            session.setAttribute("amount", amount);

            // Redirect to Razorpay Payment Page
            response.sendRedirect("payment.jsp?order_id=" + order.get("id") + "&amount=" + amount);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("select_class.jsp?msg=Payment Failed");
        }
    }
}




