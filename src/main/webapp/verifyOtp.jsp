<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verify OTP</title>
</head>
<body>
    <h2>Enter OTP Sent to Your Mobile</h2>
    <form action="VerifyOTPServlet" method="post">
        <input type="text" name="otp" placeholder="Enter OTP" required>
        <input type="hidden" name="mobile" value="<%= request.getParameter("mobile") %>">
        <button type="submit">Verify OTP</button>
    </form>
</body>
</html>
