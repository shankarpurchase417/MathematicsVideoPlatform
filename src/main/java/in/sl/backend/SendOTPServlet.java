package in.sl.backend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONObject;

@WebServlet("/SendOTPServlet")
public class SendOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String API_KEY = "75bfcdd7-e482-11ef-8b17-0200cd936042";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mobile = request.getParameter("mobile");

        // Validate Mobile Number
        if (mobile == null ||!mobile.matches("8741898523") || !mobile.matches("\\d{10}")) {
            response.getWriter().println("Invalid Mobile Number. Enter a valid 10-digit number if you are Admin plz correct admin mobile number.");
            return;
        }

        // Construct API URL to Send OTP
        String otpUrl = "https://2factor.in/API/V1/" + API_KEY + "/SMS/" + mobile + "/AUTOGEN";

        try {
            Document doc = Jsoup.connect(otpUrl).ignoreContentType(true).get();
            String apiResponse = doc.text();

            // Debugging: Print API Response
            System.out.println("OTP API Response: " + apiResponse);

            // Parse JSON Response
            JSONObject jsonResponse = new JSONObject(apiResponse);
            String status = jsonResponse.getString("Status");

            if (status.equals("Success")) {
                String sessionId = jsonResponse.getString("Details");

                // Save Session ID in HTTP Session
                HttpSession session = request.getSession();
                session.setAttribute("otp_session_id", sessionId);

                // Redirect to OTP Verification Page
                response.sendRedirect("verifyOtp.jsp?mobile=" + mobile);
            } else {
                response.getWriter().println("Error sending OTP: " + jsonResponse.getString("Details"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Server Error: " + e.getMessage());
        }
    }
}


