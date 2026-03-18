<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 3/18/2026
  Time: 6:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <%@ include file="/includes/logo.jsp" %>
  <title>Customer Registration</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<div class="form-container">
  <h2>Register New Account</h2>

  <% String error = (String) request.getAttribute("error"); %>
  <% if (error != null) { %>
  <p style="color:red;"><%= error %></p>
  <% } %>

  <form action="<%= request.getContextPath() %>/registerCustomer" method="post">
    <label for="firstName">First Name:</label>
    <input type="text" name="firstName" id="firstName" required>

    <label for="lastName">Last Name:</label>
    <input type="text" name="lastName" id="lastName" required>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>

    <button type="submit">Register</button>
  </form>
</div>

</body>
</html>