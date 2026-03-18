<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Customer" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Appointment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    Customer customer = (Customer) session.getAttribute("customer");
    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<main>
    <div class="dashboard-container">
        <h2>Customer Dashboard</h2>

        <% if (customer != null) { %>
        <p>Welcome, <%= customer.getFirstName() %> <%= customer.getLastName() %>! Manage your appointments and profile below.</p>
        <% } else { %>
        <p>Welcome! Please log in or register to access your dashboard.</p>
        <% } %>

        <div class="dashboard-cards">

            <!-- Book Appointments Card -->
            <div class="dashboard-card">
                <h3>Book Appointments</h3>
                <p>Schedule a new salon appointment with your preferred stylist.</p>
                <a href="<%= request.getContextPath() %>/customer/book-appointments.jsp" class="btn-dashboard">Book Now</a>
            </div>

            <!-- My Appointments Card -->
            <div class="dashboard-card appointments-card">
                <h3>My Appointments</h3>

                <% if (appointments != null && !appointments.isEmpty()) {
                    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
                %>

                <h4>Upcoming Appointments</h4>
                <table class="appointments-table">
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Hairdresser ID</th>
                        <th>Status</th>
                    </tr>
                    <% for (Appointment a : appointments) {
                        if (a.getStartDateTime().isAfter(LocalDateTime.now())) { %>
                    <tr>
                        <td><%= a.getStartDateTime().format(dateFmt) %></td>
                        <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
                        <td><%= a.getHairdresserId() %></td>
                        <td><%= a.getStatus() %></td>
                    </tr>
                    <% } } %>
                </table>

                <h4>Past Appointments</h4>
                <table class="appointments-table">
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Hairdresser ID</th>
                        <th>Status</th>
                    </tr>
                    <% for (Appointment a : appointments) {
                        if (a.getStartDateTime().isBefore(LocalDateTime.now())) { %>
                    <tr>
                        <td><%= a.getStartDateTime().format(dateFmt) %></td>
                        <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
                        <td><%= a.getHairdresserId() %></td>
                        <td><%= a.getStatus() %></td>
                    </tr>
                    <% } } %>
                </table>

                <% } else { %>
                <p>You have no appointments scheduled.</p>
                <% } %>
            </div>

            <!-- Profile Settings Card -->
            <div class="dashboard-card">
                <h3>Profile Settings</h3>
                <p>Update your personal info, password, and preferences.</p>
                <a href="<%= request.getContextPath() %>/customerProfile" class="btn-dashboard">Edit Profile</a>
            </div>

            <!-- Register Another Account Card -->
            <div class="dashboard-card">
                <h3>Register Another Account</h3>
                <p>Create a new customer account if needed.</p>
                <a href="<%= request.getContextPath() %>/registerCustomer" class="btn-dashboard">Register</a>
            </div>

        </div> <!-- .dashboard-cards -->
    </div> <!-- .dashboard-container -->
</main>

</body>
</html>