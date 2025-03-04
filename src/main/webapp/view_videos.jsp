<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, java.util.Base64, in.sl.backend.DBConnection" %>
<%@ page session="true" %>

<%
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("login.jsp?msg=Please login first");
        return;
    }

    // Fix: Get correct request parameters
    String classLevel = request.getParameter("class"); 
    String videoType = request.getParameter("type");

    if (classLevel == null || videoType == null) {
        response.sendRedirect("dashboard.jsp?msg=Invalid Class Selection");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT title, video_data FROM videos WHERE class_level=? AND video_type=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, classLevel);
        ps.setString(2, videoType);
        rs = ps.executeQuery();
%>

<html>
<head>
    <title>Videos for Class <%= classLevel %> (<%= videoType.toUpperCase() %>)</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Videos for Class <%= classLevel %> (<%= videoType.toUpperCase() %>)</h2>

    <%
        boolean videoFound = false;
        while (rs.next()) {
            videoFound = true;
            String title = rs.getString("title");
            byte[] videoBytes = rs.getBytes("video_data");
            if (videoBytes == null || videoBytes.length == 0) {
    %>
                <p>Error: Video data is missing for "<%= title %>".</p>
    <%
            } else {
                String base64Video = Base64.getEncoder().encodeToString(videoBytes);
    %>
                <h3><%= title %></h3>
                <video width="500" controls>
                    <source src="data:video/mp4;base64,<%= base64Video %>" type="video/mp4">
                    Your browser does not support the video tag.
                </video>
                <br><br>
    <% 
            } 
        } 
        if (!videoFound) {
    %>
        <p>No videos available for this class.</p>
    <% } %>

</body>
</html>

<%
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
%>






