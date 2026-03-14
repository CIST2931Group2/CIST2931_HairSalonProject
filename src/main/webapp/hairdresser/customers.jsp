<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/08/2026
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.dto.AssignedCustomerView" %>

<!DOCTYPE html>
<html>
<head>
  <title>My Customers</title>
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

<h1>My Customers</h1>

<!-- Error message -->
<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
  }
%>


<%
  List<AssignedCustomerView> customers =
          (List<AssignedCustomerView>) request.getAttribute("customers");
%>

<% if (customers == null || customers.isEmpty()) { %>

<p>No customers assigned yet.</p>

<% } else { %>

<table class="customers-table">

  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Phone</th>
    <th>Email</th>
    <th>Action</th>
  </tr>

  <% for (AssignedCustomerView c : customers) { %>

  <tr>
    <td><%= c.getFirstName() %></td>
    <td><%= c.getLastName() %></td>
    <td><%= c.getPhone() %></td>
    <td><%= c.getEmail() %></td>

    <td>
      <a href="<%= request.getContextPath() %>/hairdresserCustomerProfile?customerId=<%= c.getCustomerId() %>">
        View Profile
      </a>
    </td>
  </tr>

  <% } %>

</table>

<% } %>


</body>
</html>
