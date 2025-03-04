package in.sl.backend;

import com.razorpay.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get class fee from form
            String classLevel = request.getParameter("class_level");
            double amount = Double.parseDouble(request.getParameter("amount"));

            // Convert amount to paise (Razorpay works with INR paise)
            int finalAmount = (int) (amount * 100);

            // Razorpay API Credentials
            RazorpayClient razorpay = new RazorpayClient("YOUR_KEY_ID", "YOUR_KEY_SECRET");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", finalAmount);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_123456");
            orderRequest.put("payment_capture", 1);

            Order order = razorpay.orders.create(orderRequest);

            // Send Order ID to frontend
            response.setContentType("application/json");
            response.getWriter().write(order.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

