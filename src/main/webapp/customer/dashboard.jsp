<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<div class="dashboard-container">
    <h2>Customer Dashboard</h2>
    <p>Welcome! Use the navigation above to manage your appointments.</p>

    <div class="dashboard-cards">
        <div class="dashboard-card">
            <h3>Upcoming Appointments</h3>
            <p>View and manage your upcoming salon visits.</p>
            <a href="appointments.jsp" class="btn-dashboard">View Appointments</a>
        </div>

        <div class="dashboard-card">
            <h3>Your Stylists</h3>
            <p>See your favorite stylists and their schedules.</p>
            <a href="stylists.jsp" class="btn-dashboard">View Stylists</a>
        </div>

        <!-- change this so profile settings can be edited in a different page -->
        <div class="dashboard-card">
            <h3>Profile Settings</h3>
            <p>Update your profile, password, and preferences.</p>
            <a href="profile.jsp" class="btn-dashboard">Edit Profile</a>
        </div>
    </div>
</div>
</body>
</html>
