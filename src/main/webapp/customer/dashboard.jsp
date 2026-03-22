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

<main class="dashboard-main">
    <div class="dashboard-wrapper">

        <!-- Left Column: Cards / Buttons -->
        <div class="dashboard-left">
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
                        <a href="<%= request.getContextPath() %>/searchAvailability" class="btn-dashboard">Book Now</a>
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
                </div>
            </div>
        </div>

        <!-- Right Column: Appointments Table -->
        <div class="dashboard-right">
            <div class="dashboard-container appointments-scroll">
                <h2>Appointments</h2>

                <% if (appointments != null && !appointments.isEmpty()) {
                    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
                %>

                <table class="appointments-table">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Hairdresser Name</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Appointment a : appointments) {
                        String rowClass = a.getStartDateTime().isAfter(LocalDateTime.now()) ? "upcoming" : "past"; %>
                    <tr class="<%= rowClass %>">
                        <td><%= a.getStartDateTime().format(dateFmt) %></td>
                        <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
                        <td><%= a.getHairdresserId() %></td>
                        <td><%= a.getStatus() %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <% } else { %>
                <p>No appointments scheduled.</p>
                <% } %>
            </div>
        </div>

    </div> <!-- dashboard-wrapper -->
</main>

</body>
</html>