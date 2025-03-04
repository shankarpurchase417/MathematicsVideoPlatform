<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>
<%@ page session="true" %>

<%
    // Check if user is logged in
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = (int) session.getAttribute("user_id");
    boolean hasPaid = false;
    String paidClass = "";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT has_paid, paid_class FROM users WHERE id=?");
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        if (rs.next()) {
            hasPaid = rs.getBoolean("has_paid");
            paidClass = rs.getString("paid_class");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
%>

<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Welcome to Your Dashboard</h2>

    <% if (hasPaid) { %>
        <h3>You have full access to <%= paidClass %> videos</h3>
        <a href="view_videos.jsp?class=<%= paidClass %>"><button>View Full Videos</button></a>
    <% } else { %>
        <h3>Only Demo Videos Available</h3>
        <h2>View Demo Videos</h2>
        <ul>
            <li><a href="view_videos.jsp?class=10&type=demo"><button>Class 10 Demo Videos</button></a></li>
            <li><a href="view_videos.jsp?class=11&type=demo"><button>Class 11 Demo Videos</button></a></li>
            <li><a href="view_videos.jsp?class=12&type=demo"><button>Class 12 Demo Videos</button></a></li>
        </ul>

        <br><br>
        <a href="select_class.jsp"><button>Pay Now for Full Access</button></a>
    <% } %>
</body>
</html>










