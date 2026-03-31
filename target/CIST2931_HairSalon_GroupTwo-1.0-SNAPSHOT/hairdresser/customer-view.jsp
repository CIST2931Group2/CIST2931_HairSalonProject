<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/07/2026
  Time: 3:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.dto.CustomerAppointmentView" %>

<%
  Customer customer = (Customer) request.getAttribute("customer");
  String customerEmail = (String) request.getAttribute("customerEmail");
  List<CustomerAppointmentView> appointments =
          (List<CustomerAppointmentView>) request.getAttribute("appointments");

  String error = (String) request.getAttribute("error");

  DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Customer View</title>
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
    <h2 style="text-align:center;">Customer View</h2>
    <!-- Customer Profile Table -->
    <div class="dashboard-card appointments-card">
    <h3 style="text-align:center;">Customer Profile</h3>

      <% if (error != null) { %>
      <p style="text-align: center; color:red;background:#FFE6E6; padding:10px; border-radius:5px;"><%= error %></p>
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
    </div>

    <!-- All Customer Appointments Table (past + future) -->
    <div class="dashboard-card appointments-card">
    <h3 style="text-align:center;">Appointment History</h3>

    <%
      if (appointments != null && !appointments.isEmpty()) {
    %>
      <table class="appointments-table">
        <tr>
          <th>Appointment ID</th>
          <th>Date</th>
          <th>Time</th>
          <th>Hairdresser Name</th>
          <th>Service Type</th>
          <th>Status</th>
        </tr>

        <%
          for (CustomerAppointmentView a : appointments) {
        %>
        <tr>
          <td><%= a.getAppointmentId() %></td>
          <td><%= a.getStartDateTime().format(dateFmt) %></td>
          <td><%= a.getStartDateTime().format(timeFmt) %> - <%= a.getEndDateTime().format(timeFmt) %></td>
          <td><%= a.getHairdresserFirstName() %> <%= a.getHairdresserLastName() %></td>
          <td><%= a.getServiceType() %></td>
          <td><%= a.getStatus() %></td>
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
    </div>

  </div>

</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
