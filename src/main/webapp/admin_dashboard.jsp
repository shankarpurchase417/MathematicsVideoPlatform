<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession adminSession = request.getSession(false);
    if (adminSession == null || adminSession.getAttribute("admin_username") == null) {
        response.sendRedirect("admin_login.jsp");
    }
%>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Welcome, Admin</h1>
    <a href="upload_video.jsp">Upload Video</a>
    <a href="view_users.jsp">View Users</a>
    <a href="manage_video.jsp">Delete Video</a>
    <a href="class_fees.jsp">Manage Class Fees</a>
    <a href="AdminLogoutServlet">Logout</a>
</body>
</html>

