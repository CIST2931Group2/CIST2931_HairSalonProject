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
  <meta charset="UTF-8">
  <title>My Customers</title>
  <link rel="stylesheet" href="css/styles.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
  <h1 style="text-align:center;">My Customers</h1>

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

</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
