<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    HttpSession sessionObj = request.getSession();
    String userEmail = (String) sessionObj.getAttribute("userEmail"); // Get userEmail from session

    if (userEmail == null) {
        response.sendRedirect("login.jsp"); // अगर Email null है, तो लॉगिन पेज पर भेज दें
    }
%>

<html>
<head>
    <title>Select Your Class</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Select Your Class</h2>
    <form action="DatabaseUpdateServlet" method="post">
        <input type="hidden" name="userEmail" value="<%= userEmail %>"> <!-- Session से Email -->

        <label for="class_name">Select Class:</label>
        <select name="class_name" id="class_name" required>
            <option value="10">Class 10</option>
            <option value="11">Class 11</option>
            <option value="12">Class 12</option>
        </select>

        <button type="submit">Update Class</button>
    </form>
</body>
</html>

