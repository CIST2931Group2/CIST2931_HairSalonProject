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
<%@ page import="com.example.cist2931_hairsalon_grouptwo.dto.CustomerAppointmentView" %>

<%
    Customer customer = (Customer) request.getAttribute("customer");
    List<CustomerAppointmentView> appointments = (List<CustomerAppointmentView>) request.getAttribute("appointments");
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
        <p>Welcome! Book an Appointment, manage your Profile, view Upcoming and Past Appointments.</p>
        <% } %>

      <div class="dashboard-cards">
        <div class="dashboard-buttons-column">

            <!-- Book Appointments Card -->
            <div class="dashboard-card">
                <h3>Book Appointments</h3>
                <p>Schedule a new salon appointment with your preferred stylist.</p>
                <a href="<%= request.getContextPath() %>/searchAvailability" class="btn-dashboard">Book Now</a>
            </div>


            <!-- Profile Settings Card -->
            <div class="dashboard-card">
                <h3>Profile Settings</h3>
                <p>Update your personal info.</p>
                <a href="<%= request.getContextPath() %>/customerProfile" class="btn-dashboard">Edit Profile</a>
            </div>

            <!-- Register Another Account Card -->
            <div class="dashboard-card">
                <h3>Register Another Account</h3>
                <p>Create a new customer account if needed.</p>
                <a href="<%= request.getContextPath() %>/registerCustomer" class="btn-dashboard">Register</a>
            </div>
          </div> <!-- end left column  -->

          <div class="dashboard-appointments-column">
            <!-- My Appointments Card -->
            <div class="dashboard-card appointments-card">
                <h3>My Appointments</h3>

                <% if (appointments != null && !appointments.isEmpty()) {
                    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
                %>

                <h4>Upcoming Appointments</h4>
                <table class="appointments-table">
                    <tr>
                        <th>Appointment ID</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Hairdresser Name</th>
                        <th>Service Type</th>
                        <th>Status</th>
                    </tr>
                    <% for (CustomerAppointmentView a : appointments) {
                        if (a.getStartDateTime().isAfter(LocalDateTime.now())) { %>
                    <tr>
                        <td><%= a.getAppointmentId() %></td>
                        <td><%= a.getStartDateTime().format(dateFmt) %></td>
                        <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
                        <td><%= a.getHairdresserFirstName() %> <%= a.getHairdresserLastName() %></td>
                        <td><%= a.getServiceType() %></td>
                        <td><%= a.getStatus() %></td>
                    </tr>
                    <% } } %>
                </table>

                <h4>Past Appointments</h4>
                <table class="appointments-table">
                    <tr>
                        <th>Appointment ID</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Hairdresser Name</th>
                        <th>Service Type</th>
                        <th>Status</th>
                    </tr>
                    <% for (CustomerAppointmentView a : appointments) {
                        if (a.getStartDateTime().isBefore(LocalDateTime.now())) { %>
                    <tr>
                        <td><%= a.getAppointmentId() %></td>
                        <td><%= a.getStartDateTime().format(dateFmt) %></td>
                        <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
                        <td><%= a.getHairdresserFirstName() %> <%= a.getHairdresserLastName() %></td>
                        <td><%= a.getServiceType() %></td>
                        <td><%= a.getStatus() %></td>
                    </tr>
                    <% } } %>
                </table>

                <% } else { %>
                <p>You have no appointments scheduled.</p>
                <% } %>
            </div>
          </div> <!-- end right column -->
        </div> <!-- .dashboard-cards -->
    </div> <!-- .dashboard-container -->
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>