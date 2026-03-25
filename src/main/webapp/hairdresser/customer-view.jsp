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

<main class = "flex-container">

  <h2 style="text-align:center;">Customer Profile</h2>

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
  <br>

  <h2 style="text-align:center;">Appointment History</h2>

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

</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
