<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Customer" %>

<%
    Customer customer = (Customer) session.getAttribute("customer");
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

            <div class="dashboard-card">
                <h3>Book Appointments</h3>
                <p>Schedule a new salon appointment with your preferred stylist.</p>
                <a href="<%= request.getContextPath() %>/customer/book-appointments" class="btn-dashboard">Book Now</a>
            </div>

            <div class="dashboard-card">
                <h3>Your Stylists</h3>
                <p>View your favorite stylists and their schedules.</p>
                <a href="<%= request.getContextPath() %>/customer/stylists.jsp" class="btn-dashboard">View Stylists</a>
            </div>

            <div class="dashboard-card">
                <h3>Profile Settings</h3>
                <p>Update your personal info, password, and preferences.</p>
                <a href="<%= request.getContextPath() %>/customer/customer-profile" class="btn-dashboard">Edit Profile</a>
            </div>

            <div class="dashboard-card">
                <h3>Register Another Account</h3>
                <p>Create a new customer account if needed.</p>
                <a href="<%= request.getContextPath() %>/register.jsp" class="btn-dashboard">Register</a>
            </div>

        </div> <!-- .dashboard-cards -->
    </div> <!-- .dashboard-container -->
</main>

</body>
</html>