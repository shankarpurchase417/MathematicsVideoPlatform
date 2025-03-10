<%@ page import="java.sql.*, in.sl.backend.DBConnection" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp?msg=Please login first");
        return;
    }

    int userId = (int) session.getAttribute("user_id");
    boolean hasPaid = false;
    String paidClass = "";

    Connection conn = DBConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT has_paid, paid_class FROM users WHERE id=?");
    ps.setInt(1, userId);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        hasPaid = rs.getBoolean("has_paid");
        paidClass = rs.getString("paid_class");
    }
%>

<html>
<head><title>Dashboard</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Welcome to Dashboard</h2>
    <% if (hasPaid) { %>
        <h3>You have full access to <%= paidClass %> videos</h3>
        
        <a href="view_videos.jsp?class=<%= paidClass %>&type=full"><button>Watch Full Videos</button></a>
        <a href="login.jsp?class=<%= paidClass %>&type=full"><button>Logout</button></a>
    <% } else { %>
        <h3>Only Demo Videos Available</h3>
        <ul>
            <li><a href="view_videos.jsp?class=10&type=demo"><button>Class 10 Demo Videos</button></a></li>
            <li><a href="view_videos.jsp?class=11&type=demo"><button>Class 11 Demo Videos</button></a></li>
            <li><a href="view_videos.jsp?class=12&type=demo"><button>Class 12 Demo Videos</button></a></li>
        </ul>
        <a href="payment.jsp"><button>Pay Now</button></a>
    <% } %>
</body>
</html>











