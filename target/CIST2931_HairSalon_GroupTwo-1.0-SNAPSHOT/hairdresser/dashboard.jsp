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

    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Salon Appointment System - Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>
<!-- Navigation -->
<jsp:include page="/includes/hairdresser-nav.jsp" />

<main>
    <div class="dashboard-container" style="align-content: flex-start;">
    <h2 style="text-align: center;">HAIRDRESSER DASHBOARD</h2>

    <% if (error != null) { %>
    <p style="text-align: center; color:red;background:#FFE6E6; padding:10px; border-radius:5px;"><%= error %></p>
    <% } %>

    <% if (hairdresser != null) { %>
    <h3 style="text-align: center;">
        Welcome, <%= hairdresser.getFirstName() %> <%= hairdresser.getLastName() %>
    </h3>
    <% } %>

    <div class="dashboard-card appointments-card">
    <h3 style="text-align: center;">Daily Appointments</h3>

    <form class="form-date" method="get" action="<%= request.getContextPath() %>/hairdresserDashboard">
        <label for="date">Select Date:</label>
        <input type="date"
               id="date"
               name="date"
               value="<%= selectedDate != null ? selectedDate.toString() : "" %>">
        <button type="submit">View</button>
    </form>

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
    </div>
    </div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
