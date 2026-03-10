<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.dto.DailyAppointmentView" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Hairdresser hairdresser = (Hairdresser) request.getAttribute("hairdresser");
    LocalDate selectedDate = (LocalDate) request.getAttribute("selectedDate");
    List<DailyAppointmentView> appointments =
            (List<DailyAppointmentView>) request.getAttribute("appointments");

    String error = (String) request.getAttribute("error");

    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Salon Appointment System - Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/hairdresser-nav.jsp" />


<h1>Hairdresser Dashboard</h1>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<% if (hairdresser != null) { %>
<h3>
    Welcome, <%= hairdresser.getFirstName() %> <%= hairdresser.getLastName() %>
</h3>
<% } %>

<!-- Navigation -->
<p>
    <a href="<%= request.getContextPath() %>/hairdresserCustomers">My Customers</a>
    |
    <a href="<%= request.getContextPath() %>/hairdresserProfile">My Profile</a>
    |
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</p>

<hr>

<h2>Daily Appointments</h2>

<form method="get" action="<%= request.getContextPath() %>/hairdresserDashboard">
    <label for="date">Select Date:</label>
    <input type="date"
           id="date"
           name="date"
           value="<%= selectedDate != null ? selectedDate.toString() : "" %>">
    <button type="submit">View</button>
</form>

<br>

<table class="appointments-table">
    <thead>
    <tr>
        <th>Time</th>
        <th>Customer</th>
        <th>Service</th>
        <th>Status</th>
    </tr>
    </thead>

    <tbody>
    <%
        if (appointments != null && !appointments.isEmpty()) {
            for (DailyAppointmentView a : appointments) {
    %>
    <tr>
        <td><%= a.getStartDateTime().toLocalTime().format(timeFmt) %></td>

        <td>
            <a href="<%= request.getContextPath() %>/hairdresserCustomerProfile?customerId=<%= a.getCustomerId() %>">
                <%= a.getCustomerFirstName() %> <%= a.getCustomerLastName() %>
            </a>
        </td>

        <td><%= a.getServiceType() %></td>
        <td><%= a.getStatus() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">No appointments scheduled for this date.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
