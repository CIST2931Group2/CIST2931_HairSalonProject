<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/08/2026
  Time: 6:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>
<%
  Hairdresser hairdresser = (Hairdresser) request.getAttribute("hairdresser");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Hairdresser Profile</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header class="site-header">
  <%@ include file="/includes/logo.jsp" %>
  <span class="site-title">Salon Appointment System</span>
</header>

<!-- Navigation -->
<nav class="nav">
  <a href="<%= request.getContextPath() %>/hairdresserDashboard">My Dashboard</a>
  |
  <a href="<%= request.getContextPath() %>/hairdresserCustomers">My Customers</a>
  |
  <a href="<%= request.getContextPath() %>/hairdresserProfile">My Profile</a>
  |
  <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>

<main class = "flex-container">
  <h1 style="text-align: center;">My Profile</h1>

  <!-- Error message -->
  <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
  %>
  <p style="color:red;"><%= error %></p>
  <%
    }
  %>

  <!-- Success message -->
  <%
    String success = (String) request.getAttribute("success");
    if (success != null) {
  %>
  <p style="color:green;"><%= success %></p>
  <%
    }
  %>

  <form class="form-container" method="post" action="<%= request.getContextPath() %>/hairdresserProfile">

    <label>Email:</label>
    <input type="email"
           value="<%= session.getAttribute("email") != null ? session.getAttribute("email") : "" %>"
           readonly>

    <label>First Name:</label>
    <input type="text" name="firstName"
           value="<%= hairdresser != null ? hairdresser.getFirstName() : "" %>"
           required>

    <label>Last Name:</label>
    <input type="text" name="lastName"
           value="<%= hairdresser != null ? hairdresser.getLastName() : "" %>"
           required>

    <label>Phone:</label>
    <input type="text" name="phone"
           value="<%= hairdresser != null ? hairdresser.getPhone() : "" %>">

    <label>Specialties:</label>
    <input type="text" name="specialties"
           value="<%= hairdresser != null ? hairdresser.getSpecialties() : "" %>">

    <label>Bio:</label>
    <textarea name="bio" rows="4" cols="40"><%= hairdresser != null ? hairdresser.getBio() : "" %></textarea>

    <button type="submit">Save Profile</button>

  </form>

  <br>
</main>

</body>
</html>
