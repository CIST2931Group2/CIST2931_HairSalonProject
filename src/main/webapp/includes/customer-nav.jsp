<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="nav">
  <!-- Dashboard -->
  <a href="<%= request.getContextPath() %>/customer/dashboard.jsp">Dashboard</a>

  <!-- Book Appointment -->
  <a href="<%= request.getContextPath() %>/customer/book-appointments.jsp">Book Appointment</a>

  <!-- Profile -->
  <a href="<%= request.getContextPath() %>/customer/customer-profile.jsp">Profile</a>

  <!-- Logout -->
  <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>