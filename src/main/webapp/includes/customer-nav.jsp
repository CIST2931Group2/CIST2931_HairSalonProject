<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="nav">
  <!-- Dashboard -->
  <a href="<%= request.getContextPath() %>/customerDashboard">Dashboard</a>

  <!-- Book Appointment (link to JSP page that will submit to servlet) -->
  <a href="<%= request.getContextPath() %>/customer/book-appointments.jsp">Book Appointment</a>

  <!-- Profile -->
  <a href="<%= request.getContextPath() %>/customerProfile">Profile</a>

  <!-- Logout -->
  <form action="<%= request.getContextPath() %>/logout" method="post" style="display:inline;">
    <button type="submit" class="btn-small">Logout</button>
  </form>
</nav>