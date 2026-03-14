<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/07/2026
  Time: 3:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Customer" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Appointment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
  Customer customer = (Customer) request.getAttribute("customer");
  String customerEmail = (String) request.getAttribute("customerEmail");
  List<Appointment> appointments =
          (List<Appointment>) request.getAttribute("appointments");

  String error = (String) request.getAttribute("error");

  DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Customer View</title>
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

<h2>Customer Profile</h2>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<% if (customer != null) { %>

<table>
  <tr>
    <td><strong>First Name:</strong></td>
    <td><%= customer.getFirstName() %></td>
  </tr>
  <tr>
    <td><strong>Last Name:</strong></td>
    <td><%= customer.getLastName() %></td>
  </tr>
  <tr>
    <td><strong>Phone:</strong></td>
    <td><%= customer.getPhone() %></td>
  </tr>
  <tr>
    <td><strong>Email:</strong></td>
    <td><%= customerEmail != null ? customerEmail : "" %></td>
  </tr>
</table>

<br>

<h3>Appointment History</h3>

<%
  if (appointments != null && !appointments.isEmpty()) {
%>
<table class="appointments-table">
  <tr>
    <th>Date</th>
    <th>Time</th>
    <th>Service</th>
    <th>Status</th>
  </tr>

  <%
    for (Appointment appt : appointments) {
  %>
  <tr>
    <td><%= appt.getStartDateTime().format(dateFmt) %></td>
    <td><%= appt.getStartDateTime().format(timeFmt) %></td>
    <td><%= appt.getServiceType() %></td>
    <td><%= appt.getStatus() %></td>
  </tr>
  <%
    }
  %>
</table>
<%
} else {
%>
<p>No appointments found for this customer.</p>
<%
  }
%>

<% } %>

<br>

</body>
</html>
