<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get video ID from request
    String videoId = request.getParameter("id");

    // Validate video ID (only allow numbers)
    if (videoId == null || !videoId.matches("\\d+")) {
        response.sendRedirect("dashboard.jsp?msg=Invalid Video ID");
        return;
    }
%>
<html>
<head>
    <title>Watch Video</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Video Player</h2>
        <video width="800" controls>
            <source src="StreamVideoServlet?id=<%= videoId %>" type="video/mp4">
            Your browser does not support the video tag.
        </video>
        <br>
        <a href="dashboard.jsp">Back to Dashboard</a>
    </div>
</body>
</html>

