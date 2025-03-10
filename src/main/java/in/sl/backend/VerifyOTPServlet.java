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

@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String API_KEY = "75bfcdd7-e482-11ef-8b17-0200cd936042";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");

        // Retrieve Session ID from HTTP Session
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("otp_session_id");

        if (sessionId == null) {
            response.getWriter().println("Session expired. Please request a new OTP.");
            return;
        }

        // Construct API URL to Verify OTP
        String verifyUrl = "https://2factor.in/API/V1/" + API_KEY + "/SMS/VERIFY/" + sessionId + "/" + otp;

        try {
            Document doc = Jsoup.connect(verifyUrl).ignoreContentType(true).get();
            String apiResponse = doc.text();

            // Debugging: Print API Response
            System.out.println("OTP Verification API Response: " + apiResponse);

            // Parse JSON Response
            JSONObject jsonResponse = new JSONObject(apiResponse);
            String status = jsonResponse.getString("Status");

            if (status.equals("Success")) {
                response.sendRedirect("admin_login.jsp");
            } else {
                response.getWriter().println("Invalid OTP or OTP expired. Please request a new OTP.<br>API Response: " + jsonResponse.getString("Details"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Server Error: " + e.getMessage());
        }
    }
}




