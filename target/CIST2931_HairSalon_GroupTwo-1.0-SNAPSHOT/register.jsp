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
  <title>Customer Registration</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<!-- removed customer nav -->

<%
  String success = request.getParameter("success");
  if ("accountCreated".equals(success)) {
%>
<div id="successPopup" style="
    position: fixed;
    top: 20%;
    left: 50%;
    transform: translateX(-50%);
    background-color: #e6ffe6;
    border: 1px solid green;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    z-index: 9999;
    box-shadow: 0 0 10px rgba(0,0,0,0.3);
">
  <p style="color: green; font-weight: bold;">
    Account created successfully! 🎉
  </p>
  <button onclick="window.location.href='<%= request.getContextPath() %>/customerDashboard'"
          style="padding: 10px 20px; font-size: 1rem; cursor: pointer;">
    Go to Dashboard
  </button>
</div>

<script>
  setTimeout(() => {
    const popup = document.getElementById("successPopup");
    if (popup) popup.style.display = "none";
  }, 5000); // auto-hide after 5 seconds
</script>
<% } %>

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

    <!-- added field for phone number -->
    <label for="phone">Phone Number:</label>
    <input type="text" name="phone" id="phone" required>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>

    <button type="submit">Register</button>
    <!-- added return to dashboard button -->
    <a href="<%= request.getContextPath()%>/customerDashboard" class="btn-secondary">Return to Dashboard</a>

  </form>
</div>

</body>
</html>