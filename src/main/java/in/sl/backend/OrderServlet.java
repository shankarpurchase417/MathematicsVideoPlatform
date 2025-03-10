package in.sl.backend;

import com.razorpay.*;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // ✅ Razorpay Client (Test API Keys)
            RazorpayClient client = new RazorpayClient("rzp_test_cmsgJBXhaTTYHW", "GfadWZEoSXR2PSK5IgJPUqRL");

            // ✅ Order Amount (Paise me dena hota hai, ₹500 = 50000 paise)
            int amount = Integer.parseInt(request.getParameter("amount")) * 100;

            // ✅ Order Details Set Karein
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_12345");

            // ✅ Order Create Karein
            Order order = client.orders.create(orderRequest);
            response.getWriter().write(order.toString());

        } catch (RazorpayException e) {
            e.printStackTrace();
        }
    }
}

