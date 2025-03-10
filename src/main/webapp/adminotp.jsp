<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>OTP Verification</title>
    <script>
        function showOtpField() {
            document.getElementById("otp-section").style.display = "block";
        }
    </script>
</head>
<body>
    <h2>Enter Your Mobile Number</h2>
    <form action="SendOTPServlet" method="post">
        <input type="text" name="mobile" placeholder="Enter Mobile Number" required>
        <button type="submit">Send OTP</button>
    </form>

    <div id="otp-section" style="display: none;">
        <h2>Enter OTP</h2>
        <form action="VerifyOTPServlet" method="post">
            <input type="text" name="otp" placeholder="Enter OTP" required>
            <input type="hidden" name="mobile" value="<%= request.getParameter("mobile") %>">
            <button type="submit">Verify OTP</button>
        </form>
    </div>
</body>
</html>
