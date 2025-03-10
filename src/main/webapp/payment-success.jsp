<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession();
    String userEmail = (String) sessionObj.getAttribute("userEmail");

    if (userEmail == null) {
        response.sendRedirect("login.jsp"); // अगर Email null है, तो लॉगिन पेज पर भेज दें
    } else {
        // ✅ Redirect to class selection page
        response.sendRedirect("class-selection.jsp?userEmail=" + userEmail);
    }
%>
